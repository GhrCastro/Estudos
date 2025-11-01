# tree.py
# Árvores de decisão ID3, C4.5 e CART (do zero), compatíveis com as classes de nó/aresta em rule.py

from typing import Any, Dict, List, Optional, Tuple, Union
import math
from collections import Counter
import pandas as pd
import numpy as np

# -------------------------------------------------------
# IMPORTANTE NO SEU PROJETO:
# from rule import Connection, DecisionRuleID3, DecisionRuleC45, DecisionRuleCART
# -------------------------------------------------------

# (Apenas para preview local — REMOVA no seu projeto)
try:
    from rule import Connection, DecisionRuleID3, DecisionRuleC45, DecisionRuleCART  # type: ignore
except Exception:
    class _MockCell:
        def __init__(self, name, frequency): self.name, self.frequency = name, frequency
    class Connection: 
        def __init__(self, result_name): self.result_name, self.rule = result_name, None
    class _Base:
        def __init__(self, attribute, score, data, data_results, level):
            from collections import Counter
            self.attribute = attribute
            self.level = level
            self.connections = []
            counts = Counter(data_results)
            (self.result, self.result_frequencia) = list(counts.items())[0]
            self.result_ns = [ _MockCell(k,v) for k,v in counts.items() ]
            self.data_n = len(data_results)
    class DecisionRuleID3(_Base):
        def __init__(self, attribute, information_gain, data, data_results, level):
            self.information_gain = information_gain
            super().__init__(attribute, information_gain, data, data_results, level)
    class DecisionRuleC45(_Base):
        def __init__(self, attribute, gain_ratio, data, data_results, level):
            self.gain_ratio = gain_ratio; self.value=None
            super().__init__(attribute, gain_ratio, data, data_results, level)
    class DecisionRuleCART(_Base):
        def __init__(self, attribute, gini, data, data_results, level):
            self.gini = gini; self.value=None
            super().__init__(attribute, gini, data, data_results, level)


# ==============================
# Métricas a partir da confusão
# ==============================
def calculate_metrics(confusion_matrix: pd.DataFrame) -> pd.DataFrame:
    """
    Calcula métricas por classe a partir de uma matriz de confusão (linhas = verdade, colunas = predito).
    Retorna um DataFrame com VP, FN, VN, FP, Precision, Recall, F1-Score e Accuracy por classe.
    """
    classes = list(confusion_matrix.index)
    result = pd.DataFrame(index=classes, columns=['VP','FN','VN','FP','Precision','Recall','F1-Score','Accuracy'], dtype=float).fillna(0.0)

    for c in classes:
        vp = float(confusion_matrix.loc[c, c])
        fn = float(confusion_matrix.loc[c, :].sum() - vp)
        fp = float(confusion_matrix.loc[:, c].sum() - vp)
        vn = float(confusion_matrix.to_numpy().sum() - (vp + fn + fp))
        result.loc[c, ['VP','FN','VN','FP']] = [vp, fn, vn, fp]

    for c in classes:
        vp, fn, vn, fp = result.loc[c, ['VP','FN','VN','FP']]
        precision = vp/(vp+fp) if (vp+fp)>0 else 0.0
        recall    = vp/(vp+fn) if (vp+fn)>0 else 0.0
        f1        = (2*precision*recall/(precision+recall)) if (precision+recall)>0 else 0.0
        accuracy  = (vp+vn)/(vp+vn+fp+fn) if (vp+vn+fp+fn)>0 else 0.0
        result.loc[c, ['Precision','Recall','F1-Score','Accuracy']] = [precision, recall, f1, accuracy]

    return result


# ==============================
# Árvore base: utilidades comuns
# ==============================
class _BaseTree:
    """
    Base para ID3, C4.5 e CART.
    - max_height: altura máxima da árvore (-1 = ilimitada)
    - min_threshold: limite mínimo do critério para efetuar split (ganho/razão/gini)
    """
    def __init__(self, max_height: int = -1, min_threshold: float = 0.0, random_state: Optional[int] = None):
        self.max_height = max_height
        self.min_threshold = min_threshold
        self.random_state = random_state
        self.root = None
        self._is_c45 = False
        self._is_cart = False

    # ---------- helpers genéricos ----------
    @staticmethod
    def _entropy(labels: List[Any], log_base: float = 2.0) -> float:
        n = len(labels)
        if n == 0: 
            return 0.0
        counts = Counter(labels)
        ent = 0.0
        for c in counts.values():
            p = c / n
            if p > 0:
                ent -= p * math.log(p, log_base)
        return ent

    @staticmethod
    def _gini(labels: List[Any]) -> float:
        n = len(labels)
        if n == 0:
            return 0.0
        counts = Counter(labels)
        s = sum((c/n)**2 for c in counts.values())
        return 1.0 - s

    @staticmethod
    def _midpoints(sorted_unique: np.ndarray) -> List[float]:
        return [(a+b)/2.0 for a,b in zip(sorted_unique[:-1], sorted_unique[1:])]

    @staticmethod
    def _is_numeric(series: pd.Series) -> bool:
        return pd.api.types.is_numeric_dtype(series)

    def _stopping(self, level: int, labels: List[Any]) -> bool:
        if len(set(labels)) <= 1: 
            return True
        if self.max_height != -1 and level >= self.max_height:
            return True
        return False

    # ---------- interface pública ----------
    def fit(self, X: pd.DataFrame, y: Union[pd.Series, List[Any]]):
        y_list = list(y.values if isinstance(y, pd.Series) else y)
        self.root = self._build(X.copy(), y_list, 0)
        return self

    def predict_one(self, row: pd.Series) -> Any:
        node = self.root
        while isinstance(node, (DecisionRuleID3, DecisionRuleC45, DecisionRuleCART)):
            attr = node.attribute
            # Se C4.5/CART com limiar numérico
            if hasattr(node, "value") and node.value is not None and self._is_numeric(row.get(attr, np.nan)):
                cond = row[attr] <= node.value
                branch = "<=" if cond else ">"
            else:
                # categórico: procurar ramificação do valor
                branch = row.get(attr, None)
            # seguir conexão correspondente
            next_node = None
            for c in node.connections:
                if (hasattr(node, "value") and node.value is not None and (c.result_name in ("<=", ">"))) or True:
                    if c.result_name == ("<=" if hasattr(node, "value") and node.value is not None and self._is_numeric(row.get(attr, np.nan)) and row[attr] <= node.value else c.result_name):
                        if hasattr(node, "value") and node.value is not None and c.result_name in ("<=", ">"):
                            next_node = c.rule
                            break
                if c.result_name == branch:
                    next_node = c.rule
                    break
            if next_node is None:
                # fallback: se não achou, retorna classe majoritária do nó
                return getattr(node, "result", None)
            node = next_node
        # nó folha: devolve classe
        return node  # folhas são rotuladas diretamente com a classe

    def predict(self, X: pd.DataFrame) -> List[Any]:
        return [self.predict_one(X.iloc[i]) for i in range(len(X))]

    def score(self, X: pd.DataFrame, y_true: Union[pd.Series, List[Any]]) -> float:
        y_pred = self.predict(X)
        y_true_list = list(y_true.values if isinstance(y_true, pd.Series) else y_true)
        correct = sum(1 for a,b in zip(y_true_list, y_pred) if a == b)
        return correct / len(y_true_list) if y_true_list else 0.0

    # ---------- construção da árvore (override por algoritmo) ----------
    def _build(self, X: pd.DataFrame, y: List[Any], level: int):
        raise NotImplementedError


# ==============================
# ID3 (ganho de informação)
# ==============================
class DecisionTreeID3(_BaseTree):
    def __init__(self, max_height: int = -1, min_threshold: float = 1e-12):
        super().__init__(max_height=max_height, min_threshold=min_threshold)
        self._is_c45 = False
        self._is_cart = False

    def _information_gain(self, parent_entropy: float, subsets: List[List[Any]], base: float) -> float:
        n = sum(len(s) for s in subsets)
        weighted = 0.0
        for s in subsets:
            weighted += (len(s)/n) * self._entropy(s, log_base=base)
        return parent_entropy - weighted

    def _best_split(self, X: pd.DataFrame, y: List[Any]) -> Tuple[Optional[str], float]:
        base = max(len(set(y)), 2)  # base do log = nº de classes
        parent_entropy = self._entropy(y, log_base=base)
        best_attr, best_gain = None, -1.0

        for col in X.columns:
            # ID3 supõe categóricos (Titanic deve ser discretizado previamente)
            parts = []
            for val, idx in X.groupby(col).groups.items():
                parts.append([y[i] for i in idx])
            gain = self._information_gain(parent_entropy, parts, base)
            if gain > best_gain:
                best_gain, best_attr = gain, col
        return best_attr, best_gain

    def _build(self, X: pd.DataFrame, y: List[Any], level: int):
        # paradas
        if self._stopping(level, y):
            # folha = classe majoritária
            return Counter(y).most_common(1)[0][0]

        attr, gain = self._best_split(X, y)
        node = DecisionRuleID3(attr, gain, X, y, level)

        if gain < self.min_threshold:
            return Counter(y).most_common(1)[0][0]

        # criar conexões por valor do atributo (multi-ramificado)
        for val, idx in X.groupby(attr).groups.items():
            conn = Connection(val)
            X_sub = X.iloc[idx].drop(columns=[attr])
            y_sub = [y[i] for i in idx]
            conn.rule = self._build(X_sub, y_sub, level+1)
            node.connections.append(conn)

        return node


# ==============================
# C4.5 (razão de ganho; contínuos)
# ==============================
class DecisionTreeC45(_BaseTree):
    def __init__(self, max_height: int = -1, min_threshold: float = 1e-12):
        super().__init__(max_height=max_height, min_threshold=min_threshold)
        self._is_c45 = True
        self._is_cart = False

    def _split_numeric(self, series: pd.Series, y: List[Any]) -> Tuple[Optional[float], float]:
        # varrer pontos médios
        vals = series.dropna().unique()
        if len(vals) <= 1:
            return None, -1.0
        mids = self._midpoints(np.sort(vals))
        best_thr, best_ratio = None, -1.0
        base = max(len(set(y)), 2)
        H_parent = self._entropy(y, log_base=base)

        for thr in mids:
            left_idx = series.index[series <= thr]
            right_idx = series.index[series > thr]
            if len(left_idx)==0 or len(right_idx)==0:
                continue
            y_left = [y[i] for i in left_idx]
            y_right = [y[i] for i in right_idx]
            gain = H_parent - ((len(y_left)/len(y))*self._entropy(y_left, base) + (len(y_right)/len(y))*self._entropy(y_right, base))
            split_info = 0.0
            for part in (y_left, y_right):
                p = len(part)/len(y)
                if p>0: split_info -= p*math.log(p, base)
            ratio = gain/split_info if split_info>0 else 0.0
            if ratio > best_ratio:
                best_ratio, best_thr = ratio, thr
        return best_thr, best_ratio

    def _split_categorical(self, series: pd.Series, y: List[Any]) -> float:
        base = max(len(set(y)), 2)
        H_parent = self._entropy(y, log_base=base)
        parts = []
        sizes = []
        for _, idx in series.groupby(series).groups.items():
            subset = [y[i] for i in idx]
            parts.append(subset)
            sizes.append(len(idx))

        gain = H_parent - sum((len(p)/len(y))*self._entropy(p, base) for p in parts)
        split_info = 0.0
        for s in sizes:
            p = s/len(y)
            if p>0: split_info -= p*math.log(p, base)
        return gain/split_info if split_info>0 else 0.0

    def _best_split(self, X: pd.DataFrame, y: List[Any]) -> Tuple[Optional[str], float, Optional[float]]:
        best_attr, best_ratio, best_thr = None, -1.0, None
        for col in X.columns:
            s = X[col]
            if self._is_numeric(s):
                thr, ratio = self._split_numeric(s, y)
                if ratio > best_ratio:
                    best_attr, best_ratio, best_thr = col, ratio, thr
            else:
                ratio = self._split_categorical(s, y)
                if ratio > best_ratio:
                    best_attr, best_ratio, best_thr = col, ratio, None
        return best_attr, best_ratio, best_thr

    def _build(self, X: pd.DataFrame, y: List[Any], level: int):
        if self._stopping(level, y):
            return Counter(y).most_common(1)[0][0]

        attr, ratio, thr = self._best_split(X, y)
        node = DecisionRuleC45(attr, ratio, X, y, level)
        node.value = thr  # limiar se numérico; permanece None para categórico

        if ratio < self.min_threshold:
            return Counter(y).most_common(1)[0][0]

        if thr is not None:
            # binário por limiar
            left_idx = X.index[X[attr] <= thr]
            right_idx = X.index[X[attr] > thr]
            for label, idx in (("<=", left_idx), (">", right_idx)):
                conn = Connection(label)
                X_sub = X.loc[idx].copy()
                y_sub = [y[i] for i in idx]
                conn.rule = self._build(X_sub, y_sub, level+1)
                node.connections.append(conn)
        else:
            # multi-ramificado categórico
            for val, idx in X.groupby(attr).groups.items():
                conn = Connection(val)
                X_sub = X.iloc[idx].copy()
                y_sub = [y[i] for i in idx]
                conn.rule = self._build(X_sub, y_sub, level+1)
                node.connections.append(conn)

        return node


# ==============================
# CART (gini; binário sempre)
# ==============================
class DecisionTreeCART(_BaseTree):
    def __init__(self, max_height: int = -1, min_threshold: float = 1e-12):
        super().__init__(max_height=max_height, min_threshold=min_threshold)
        self._is_c45 = False
        self._is_cart = True

    def _best_split_numeric(self, s: pd.Series, y: List[Any]) -> Tuple[Optional[float], float]:
        vals = s.dropna().unique()
        if len(vals) <= 1:
            return None, float('inf')
        mids = self._midpoints(np.sort(vals))
        best_thr, best_gini = None, float('inf')
        for thr in mids:
            left_idx = s.index[s <= thr]
            right_idx = s.index[s > thr]
            if len(left_idx)==0 or len(right_idx)==0:
                continue
            y_left = [y[i] for i in left_idx]
            y_right = [y[i] for i in right_idx]
            g = (len(y_left)/len(y))*self._gini(y_left) + (len(y_right)/len(y))*self._gini(y_right)
            if g < best_gini:
                best_gini, best_thr = g, thr
        return best_thr, best_gini

    def _best_split_categorical(self, s: pd.Series, y: List[Any]) -> Tuple[Optional[Any], float]:
        # testar partições do tipo {valor} vs {resto} (aprox. ótima e barata)
        uniq = list(pd.unique(s))
        if len(uniq) <= 1:
            return None, float('inf')
        best_val, best_gini = None, float('inf')
        for v in uniq:
            left_idx = s.index[s == v]
            right_idx = s.index[s != v]
            y_left = [y[i] for i in left_idx]
            y_right = [y[i] for i in right_idx]
            g = (len(y_left)/len(y))*self._gini(y_left) + (len(y_right)/len(y))*self._gini(y_right)
            if g < best_gini:
                best_gini, best_val = g, v
        return best_val, best_gini

    def _best_split(self, X: pd.DataFrame, y: List[Any]) -> Tuple[Optional[str], Optional[Any], float]:
        best_attr, best_param, best_score = None, None, float('inf')
        for col in X.columns:
            s = X[col]
            if self._is_numeric(s):
                thr, g = self._best_split_numeric(s, y)
                if g < best_score:
                    best_attr, best_param, best_score = col, thr, g
            else:
                val, g = self._best_split_categorical(s, y)
                if g < best_score:
                    best_attr, best_param, best_score = col, val, g
        return best_attr, best_param, best_score

    def _build(self, X: pd.DataFrame, y: List[Any], level: int):
        if self._stopping(level, y):
            return Counter(y).most_common(1)[0][0]

        attr, param, score = self._best_split(X, y)
        node = DecisionRuleCART(attr, score, X, y, level)
        node.value = param  # limiar numérico OU valor categórico para "== vs !="

        # critério mínimo: aqui interpretamos como "melhora" necessária.
        # Se não melhora (score não reduz), retorna folha.
        parent_gini = self._gini(y)
        if score >= parent_gini or (parent_gini - score) < self.min_threshold:
            return Counter(y).most_common(1)[0][0]

        if self._is_numeric(X[attr]):
            left_idx = X.index[X[attr] <= param]
            right_idx = X.index[X[attr] > param]
            for label, idx in (("<=", left_idx), (">", right_idx)):
                conn = Connection(label)
                X_sub = X.loc[idx].copy()
                y_sub = [y[i] for i in idx]
                conn.rule = self._build(X_sub, y_sub, level+1)
                node.connections.append(conn)
        else:
            # binário categórico: == value  vs  != value
            left_idx = X.index[X[attr] == param]
            right_idx = X.index[X[attr] != param]
            for label, idx in ((f"=={param}", left_idx), (f"!={param}", right_idx)):
                conn = Connection(label)
                X_sub = X.loc[idx].copy()
                y_sub = [y[i] for i in idx]
                conn.rule = self._build(X_sub, y_sub, level+1)
                node.connections.append(conn)

        return node
