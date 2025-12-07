from __future__ import annotations
import json
import math
import textwrap
from dataclasses import dataclass, asdict
from pathlib import Path
from typing import Dict, List, Tuple

import matplotlib.pyplot as plt
from matplotlib.backends.backend_pdf import PdfPages
import numpy as np
import pandas as pd
from scipy import stats
from sklearn.cluster import DBSCAN, KMeans
from sklearn.metrics import (
    calinski_harabasz_score,
    classification_report,
    confusion_matrix,
    davies_bouldin_score,
    precision_recall_fscore_support,
    silhouette_score,
)
from sklearn.model_selection import train_test_split
from sklearn.neighbors import NearestNeighbors
from sklearn.neural_network import MLPClassifier
from sklearn.preprocessing import MinMaxScaler, StandardScaler

RANDOM_STATE = 69
BASE_DIR = Path(__file__).resolve().parent
DATA_PATH = BASE_DIR.parent / "creditcard.csv"
OUTPUT_DIR = BASE_DIR / "outputs"


@dataclass
class ModelMetrics:
    scenario: str
    accuracy: float
    precision: float
    recall: float
    f1: float
    confusion: List[List[int]]
    report: Dict[str, Dict[str, float]]


@dataclass
class ClusteringMetrics:
    algorithm: str
    params: Dict[str, float]
    silhouette: float | None
    davies_bouldin: float | None
    calinski_harabasz: float | None
    n_clusters: int
    noise_ratio: float | None = None


class MiniSom:
    """Mini SOM 1D/2D simplificado para evitar dependencia externa."""

    def __init__(self, x: int, y: int, input_len: int, sigma: float = 1.0, learning_rate: float = 0.5, random_seed: int = RANDOM_STATE):
        self.x = x
        self.y = y
        self.input_len = input_len
        self.sigma = sigma
        self.learning_rate = learning_rate
        self.rng = np.random.default_rng(random_seed)
        self.weights = self.rng.random((x, y, input_len))

    def _neighborhood(self, bmu: Tuple[int, int], sigma: float) -> np.ndarray:
        xx, yy = np.meshgrid(np.arange(self.x), np.arange(self.y), indexing="ij")
        dist_sq = (xx - bmu[0]) ** 2 + (yy - bmu[1]) ** 2
        return np.exp(-dist_sq / (2 * sigma ** 2))[:, :, np.newaxis]

    def winner(self, sample: np.ndarray) -> Tuple[int, int]:
        dists = np.linalg.norm(self.weights - sample, axis=2)
        return np.unravel_index(np.argmin(dists), (self.x, self.y))

    def train_batch(self, data: np.ndarray, num_iteration: int):
        for i in range(num_iteration):
            sample = data[self.rng.integers(0, len(data))]
            bmu = self.winner(sample)
            sigma = self.sigma * math.exp(-i / num_iteration)
            lr = self.learning_rate * math.exp(-i / num_iteration)
            neigh = self._neighborhood(bmu, sigma)
            self.weights += lr * neigh * (sample - self.weights)

    def labels(self, data: np.ndarray) -> np.ndarray:
        return np.array([self.winner(x)[0] * self.y + self.winner(x)[1] for x in data])


def load_data() -> pd.DataFrame:
    if not DATA_PATH.exists():
        raise FileNotFoundError(f"Nao achei creditcard.csv em {DATA_PATH}")
    return pd.read_csv(DATA_PATH)


def remove_outliers_zscore(df: pd.DataFrame, limit: float = 3.0) -> Tuple[pd.DataFrame, int]:
    cols = [c for c in df.columns if c != "Class"]
    z_scores = np.abs(stats.zscore(df[cols], nan_policy="omit"))
    outlier_mask = (z_scores > limit) & (df[["Class"]].values == 0)
    remove_rows = outlier_mask.any(axis=1)
    removed = int(remove_rows.sum())
    df_clean = df.loc[~remove_rows].copy()
    return df_clean, removed


def analyze_correlations(df: pd.DataFrame, threshold: float = 0.5) -> List[Tuple[str, str, float]]:
    corr = df.corr()
    pairs = []
    cols = corr.columns
    for i in range(len(cols)):
        for j in range(i + 1, len(cols)):
            val = corr.iloc[i, j]
            if abs(val) > threshold:
                pairs.append((cols[i], cols[j], float(val)))
    pairs.sort(key=lambda x: abs(x[2]), reverse=True)
    return pairs


def apply_minmax_split(df: pd.DataFrame) -> Tuple[pd.DataFrame, pd.DataFrame, pd.Series, pd.Series, MinMaxScaler]:
    X = df.drop(columns=["Class"])
    y = df["Class"]
    X_train, X_test, y_train, y_test = train_test_split(
        X, y, test_size=0.2, stratify=y, random_state=RANDOM_STATE
    )
    scaler = MinMaxScaler()
    X_train_scaled = pd.DataFrame(scaler.fit_transform(X_train), columns=X_train.columns)
    X_test_scaled = pd.DataFrame(scaler.transform(X_test), columns=X_train.columns)
    return X_train_scaled, X_test_scaled, y_train.reset_index(drop=True), y_test.reset_index(drop=True), scaler


def tomek_links(X: np.ndarray, y: np.ndarray) -> Tuple[np.ndarray, np.ndarray]:
    nn = NearestNeighbors(n_neighbors=2)
    nn.fit(X)
    neighbors = nn.kneighbors(return_distance=False)[:, 1]
    keep = np.ones(len(X), dtype=bool)
    for i, j in enumerate(neighbors):
        if y[i] == y[j]:
            continue
        if neighbors[j] == i:
            if y[i] == 0 and y[j] == 1:
                keep[i] = False
            elif y[i] == 1 and y[j] == 0:
                keep[j] = False
    return X[keep], y[keep]


def smote_simple(X: np.ndarray, y: np.ndarray, random_state: int = RANDOM_STATE, k: int = 5) -> Tuple[np.ndarray, np.ndarray]:
    rng = np.random.default_rng(random_state)
    mask_min = y == 1
    mask_maj = ~mask_min
    X_min = X[mask_min]
    X_maj = X[mask_maj]
    n_min = len(X_min)
    n_maj = len(X_maj)
    if n_min == 0:
        raise ValueError("Nao ha classe minoritaria para SMOTE")
    n_neighbors = min(k, n_min)
    nn = NearestNeighbors(n_neighbors=n_neighbors)
    nn.fit(X_min)
    neighbors = nn.kneighbors(return_distance=False)
    n_to_generate = n_maj - n_min
    synthetic = []
    for _ in range(n_to_generate):
        idx = rng.integers(0, n_min)
        neigh_idx = rng.choice(neighbors[idx][1:] if n_neighbors > 1 else neighbors[idx])
        diff = X_min[neigh_idx] - X_min[idx]
        gap = rng.random()
        synthetic.append(X_min[idx] + gap * diff)
    if synthetic:
        X_syn = np.vstack(synthetic)
        y_syn = np.ones(len(X_syn), dtype=int)
        X_bal = np.vstack([X_maj, X_min, X_syn])
        y_bal = np.concatenate([np.zeros(len(X_maj), dtype=int), np.ones(len(X_min), dtype=int), y_syn])
    else:
        X_bal, y_bal = X.copy(), y.copy()
    return X_bal, y_bal


def build_training_sets(X_train: pd.DataFrame, y_train: pd.Series) -> Tuple[Dict[str, Tuple[pd.DataFrame, pd.Series]], Dict[str, Dict[str, int]]]:
    sets = {"Sem balanceamento": (X_train, y_train)}
    counts = {}
    X_np, y_np = X_train.to_numpy(), y_train.to_numpy()

    X_tomek, y_tomek = tomek_links(X_np, y_np)
    sets["TomekLinks"] = (pd.DataFrame(X_tomek, columns=X_train.columns), pd.Series(y_tomek))

    X_smote, y_smote = smote_simple(X_np, y_np)
    sets["SMOTE"] = (pd.DataFrame(X_smote, columns=X_train.columns), pd.Series(y_smote))

    X_hybrid, y_hybrid = smote_simple(X_tomek, y_tomek)
    sets["Tomek+SMOTE"] = (pd.DataFrame(X_hybrid, columns=X_train.columns), pd.Series(y_hybrid))

    for name, (_, yset) in sets.items():
        vc = pd.Series(yset).value_counts().to_dict()
        counts[name] = {0: int(vc.get(0, 0)), 1: int(vc.get(1, 0))}
    return sets, counts


def train_mlp(X_train: pd.DataFrame, y_train: pd.Series, X_test: pd.DataFrame, y_test: pd.Series, scenario: str) -> ModelMetrics:
    n_inputs = X_train.shape[1]
    n_hidden = math.ceil((n_inputs + 1) / 2)
    params = dict(
        hidden_layer_sizes=(n_hidden,),
        activation="relu",
        learning_rate="adaptive",
        learning_rate_init=0.175,
        max_iter=7000,
        random_state=RANDOM_STATE,
        tol=0.05,
        n_iter_no_change=20,
    )
    clf = MLPClassifier(**params)
    clf.fit(X_train, y_train)
    preds = clf.predict(X_test)
    report = classification_report(y_test, preds, output_dict=True, zero_division=0)
    precision, recall, f1, _ = precision_recall_fscore_support(y_test, preds, average="binary", zero_division=0)
    cm = confusion_matrix(y_test, preds)
    return ModelMetrics(
        scenario=scenario,
        accuracy=float(report.get("accuracy", 0.0)),
        precision=float(precision),
        recall=float(recall),
        f1=float(f1),
        confusion=cm.tolist(),
        report=report,
    )


def run_clustering(df: pd.DataFrame, sample_size: int = 40000) -> List[ClusteringMetrics]:
    rng = np.random.default_rng(RANDOM_STATE)
    X = df.drop(columns=["Class"]).to_numpy()
    if len(X) > sample_size:
        idx = rng.choice(len(X), size=sample_size, replace=False)
        X = X[idx]
    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)
    results: List[ClusteringMetrics] = []

    km = KMeans(n_clusters=2, n_init="auto", random_state=RANDOM_STATE)
    km_labels = km.fit_predict(X_scaled)
    results.append(compute_cluster_scores("KMeans", km_labels, X_scaled, {"n_clusters": 2}))

    db = DBSCAN(eps=1.6, min_samples=10)
    db_labels = db.fit_predict(X_scaled)
    results.append(compute_cluster_scores("DBSCAN", db_labels, X_scaled, {"eps": 1.6, "min_samples": 10}))

    som = MiniSom(x=1, y=2, input_len=X_scaled.shape[1], sigma=1.0, learning_rate=0.5, random_seed=RANDOM_STATE)
    som.train_batch(X_scaled, num_iteration=800)
    som_labels = som.labels(X_scaled)
    results.append(compute_cluster_scores("SOM", som_labels, X_scaled, {"grid": "1x2", "iterations": 800}))
    return results


def compute_cluster_scores(name: str, labels: np.ndarray, X: np.ndarray, params: Dict[str, float]) -> ClusteringMetrics:
    unique = np.unique(labels)
    noise_ratio = None
    labels_for_metrics = labels
    X_use = X
    if -1 in unique:
        mask = labels != -1
        noise_ratio = float((~mask).mean())
        labels_for_metrics = labels[mask]
        X_use = X[mask]
    if len(set(labels_for_metrics)) < 2:
        return ClusteringMetrics(name, params, None, None, None, n_clusters=len(set(labels)), noise_ratio=noise_ratio)
    return ClusteringMetrics(
        name,
        params,
        silhouette=float(silhouette_score(X_use, labels_for_metrics)),
        davies_bouldin=float(davies_bouldin_score(X_use, labels_for_metrics)),
        calinski_harabasz=float(calinski_harabasz_score(X_use, labels_for_metrics)),
        n_clusters=int(len(set(labels_for_metrics))),
        noise_ratio=noise_ratio,
    )


def write_pdf_from_md(md_text: str, output_path: Path, title: str = "Lista 9 - IA"):
    lines: List[str] = []
    for block in md_text.split("\n\n"):
        wrapped = textwrap.wrap(block, width=95) or [""]
        lines.extend(wrapped + [""])
    with PdfPages(output_path) as pdf:
        fig, ax = plt.subplots(figsize=(8.27, 11.69))
        ax.axis("off")
        y = 0.96
        ax.text(0.02, y, title, fontsize=16, fontweight="bold", va="top")
        y -= 0.05
        for line in lines:
            if y < 0.05:
                pdf.savefig(fig, bbox_inches="tight")
                plt.close(fig)
                fig, ax = plt.subplots(figsize=(8.27, 11.69))
                ax.axis("off")
                y = 0.97
            ax.text(0.02, y, line, fontsize=10, va="top")
            y -= 0.026
        pdf.savefig(fig, bbox_inches="tight")
        plt.close(fig)


def build_report(summary: Dict) -> str:
    clean = summary["cleaning"]
    sets = summary["balance"]
    class_counts = summary["class_counts"]
    corr_pairs = summary.get("corr_pairs", [])
    corr_text = ", ".join([f"{a}-{b}:{c:.3f}" for a, b, c in corr_pairs[:5]]) if corr_pairs else "nenhuma > 0.5"
    metrics_lines = []
    for m in summary["models"]:
        metrics_lines.append(
            f"{m['scenario']}: acc={m['accuracy']:.4f}, prec={m['precision']:.3f}, recall={m['recall']:.3f}, f1={m['f1']:.3f}, matriz={m['confusion']}"
        )
    cluster_lines = []
    for c in summary["clustering"]:
        cluster_lines.append(
            f"{c['algorithm']}: clusters={c['n_clusters']}, silhouette={c['silhouette']}, DB={c['davies_bouldin']}, CH={c['calinski_harabasz']}, ruido={c.get('noise_ratio')}, params={c['params']}"
        )
    text = f"""
Lista 9 - Pre-processamento e Agrupamento (base creditcard.csv)

Dados e limpeza
- Linhas originais: {clean['initial_rows']}; duplicatas removidas: {clean['duplicates_removed']}; apos limpeza: {clean['after_duplicates']}.
- Outliers removidos (zscore>3 apenas classe 0): {clean['outliers_removed']}; linhas finais: {clean['final_rows']}.
- Colunas removidas: Time (irrelevante), V2 (corr alta com Amount); total de colunas usadas: {clean['feature_cols']}.
- Valores ausentes detectados: {clean['missing_values']}.
- Distribuicao de classe apos limpeza: {class_counts}.
- Pares com correlacao > 0.5: {corr_text}.

Normalizacao, split e balanceamento
- Escala MinMax (0-1) ajustada no treino; split estratificado 80/20.
- Conjuntos de treino: {sets}.

Modelo MLP (relu, hidden_layer=(n_features+1)/2, lr adaptive, lr_init=0.175, max_iter=7000)
- Teste avaliado em todos os cenarios acima (mesmo X_test).
- Resultados:
  - {metrics_lines[0]}
  - {metrics_lines[1]}
  - {metrics_lines[2]}
  - {metrics_lines[3]}

Agrupamento (dados processados sem coluna Class; amostra <=40k, padronizada)
- {cluster_lines[0]}
- {cluster_lines[1]}
- {cluster_lines[2]}

Arquivos
- creditcard_processado.csv (dados limpos da Q1, sem Time e sem V2).
- outputs/results_summary.json, outputs/relatorio_lista9.md, outputs/relatorio_lista9.pdf.
"""
    return textwrap.dedent(text).strip()


def main():
    df_raw = load_data()
    clean_info: Dict[str, int | str | List[str]] = {
        "initial_rows": len(df_raw),
        "initial_cols": list(df_raw.columns),
        "missing_values": int(df_raw.isnull().sum().sum()),
        "duplicates_removed": 0,
        "after_duplicates": None,
        "outliers_removed": 0,
        "feature_cols": None,
        "final_rows": None,
    }

    df = df_raw.copy()
    if "Time" in df.columns:
        df = df.drop(columns=["Time"])

    duplicates = int(df.duplicated().sum())
    clean_info["duplicates_removed"] = duplicates
    df = df.drop_duplicates()
    clean_info["after_duplicates"] = len(df)

    df, removed_outliers = remove_outliers_zscore(df, limit=3.0)
    clean_info["outliers_removed"] = removed_outliers

    if "V2" in df.columns:
        df = df.drop(columns=["V2"])
    clean_info["feature_cols"] = list(df.drop(columns=["Class"]).columns)
    clean_info["final_rows"] = len(df)

    class_counts = df["Class"].value_counts().to_dict()

    corr_pairs = analyze_correlations(df)

    X_train, X_test, y_train, y_test, scaler = apply_minmax_split(df)
    training_sets, balance_counts = build_training_sets(X_train, y_train)

    models: List[ModelMetrics] = []
    for name, (Xtr, ytr) in training_sets.items():
        metrics = train_mlp(Xtr, ytr, X_test, y_test, name)
        models.append(metrics)

    clustering_results = run_clustering(df)

    processed_path = BASE_DIR / "creditcard_processado.csv"
    df.to_csv(processed_path, index=False)

    summary = {
        "cleaning": clean_info,
        "class_counts": {int(k): int(v) for k, v in class_counts.items()},
        "corr_pairs": corr_pairs,
        "balance": balance_counts,
        "models": [asdict(m) for m in models],
        "clustering": [asdict(c) for c in clustering_results],
    }

    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)
    with (OUTPUT_DIR / "results_summary.json").open("w", encoding="utf-8") as f:
        json.dump(summary, f, indent=2, ensure_ascii=False)

    report_text = build_report(summary)
    (OUTPUT_DIR / "relatorio_lista9.md").write_text(report_text, encoding="utf-8")
    write_pdf_from_md(report_text, OUTPUT_DIR / "relatorio_lista9.pdf")
    print("Concluido. Saidas em", OUTPUT_DIR)


if __name__ == "__main__":
    main()
