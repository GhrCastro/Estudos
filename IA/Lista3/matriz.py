import numpy as np
import pandas as pd

# ===== Matriz de confusão de exemplo (mesma estrutura da imagem) =====
cm = np.array([
    [10, 4, 2, 1],   # A
    [ 3, 15, 2, 0],  # B
    [ 1, 2, 20, 1],  # C
    [ 2, 2, 5, 10],  # D
])

classes = ["A", "B", "C", "D"]
total = cm.sum()

def safe_div(a, b):
    return a / b if b else 0.0

metrics = {}

for i, label in enumerate(classes):
    TP = cm[i, i]
    FN = cm[:, i].sum() - TP
    FP = cm[i, :].sum() - TP
    TN = total - (TP + FN + FP)

    # métricas clássicas
    precisao = safe_div(TP, (TP + FP))
    recall   = safe_div(TP, (TP + FN))
    f1       = safe_div(2 * precisao * recall, (precisao + recall))

    # taxas (sensibilidade/especificidade e seus complementos)
    tvp = safe_div(TP, (TP + FN))  # true positive rate (sensibilidade)
    tfn = safe_div(FN, (TP + FN))  # false negative rate
    tfp = safe_div(FP, (FP + TN))  # false positive rate
    tvn = safe_div(TN, (FP + TN))  # true negative rate (especificidade)

    metrics[label] = [precisao, recall, f1, tvp, tfn, tfp, tvn]

df_metrics = pd.DataFrame(
    metrics,
    index=["Precisao", "Recall", "F1Score", "TVP", "TFN", "TFP", "TVN"]
).T

# Versão em percentual (opcional)
df_percent = (df_metrics * 100).round(2)

print("Métricas (0-1):\n", df_metrics.round(4), "\n")
print("Métricas (%):\n", df_percent)
