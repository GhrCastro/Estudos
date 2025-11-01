from __future__ import annotations

import math
import os
import random
import sys
from typing import List

# Permite executar diretamente com "python Estudos/I.A/Lista8/main.py"
CURRENT_DIR = os.path.dirname(__file__)
if CURRENT_DIR not in sys.path:
    sys.path.insert(0, CURRENT_DIR)

from mlp import MLP
from seven_segment import (
    SEGMENTS,
    add_noise,
    bits_to_int,
    dataset_binary_coded_digits,
    int_to_bits,
    render_ascii,
)


def run_xor() -> None:
    print("\n=== Problema XOR ===")
    print("Equação de ajuste: w := w - lr * dE/dw (MSE, backprop)")

    X = [[0.0, 0.0], [0.0, 1.0], [1.0, 0.0], [1.0, 1.0]]
    T = [[0.0], [1.0], [1.0], [0.0]]

    model = MLP(n_inputs=2, n_hidden=3, n_outputs=1, seed=42)
    model.train(X, T, epochs=4000, lr=0.7, verbose_every=400)

    for x, t in zip(X, T):
        y = model.predict(x)[0]
        print(f"in={x} target={t[0]:.0f} pred={y:.3f} -> bin={(1 if y>=0.5 else 0)}")


def accuracy_digits(model: MLP, X: List[List[float]], T: List[List[float]]) -> float:
    ok = 0
    for x, t in zip(X, T):
        yb = model.predict_binary(x, thr=0.5)
        pred = bits_to_int(yb)
        truth = bits_to_int([int(v) for v in t])
        if pred == truth:
            ok += 1
    return ok / len(X)


def run_seven_segment() -> None:
    print("\n=== Dígitos no display de 7 segmentos ===")
    X, T = dataset_binary_coded_digits()

    # Rede 7-5-4 (entrada/oculta/saída)
    model = MLP(n_inputs=7, n_hidden=5, n_outputs=4, seed=7)

    # Para robustez, vamos ampliar o dataset com amostras ruidosas
    aug_X: List[List[float]] = []
    aug_T: List[List[float]] = []
    for x, t in zip(X, T):
        aug_X.append(x)
        aug_T.append(t)
        # 4 versões com ruído (apagando alguns segmentos e acendendo poucos espúrios)
        for _ in range(4):
            aug_X.append(add_noise(x, drop_prob=0.15, spur_prob=0.03))
            aug_T.append(t)

    model.train(aug_X, aug_T, epochs=3000, lr=0.5, verbose_every=300)

    # Avaliação sem ruído
    acc_clean = accuracy_digits(model, X, T)
    print(f"Acurácia (limpo): {acc_clean*100:.1f}%")

    # Avaliação com ruído (10 amostras por dígito)
    noisy_X: List[List[float]] = []
    noisy_T: List[List[float]] = []
    for d in range(10):
        base = X[d]
        t = T[d]
        for _ in range(10):
            noisy_X.append(add_noise(base, drop_prob=0.2, spur_prob=0.05))
            noisy_T.append(t)
    acc_noisy = accuracy_digits(model, noisy_X, noisy_T)
    print(f"Acurácia (ruído): {acc_noisy*100:.1f}%")

    # Prints de alguns exemplos
    print("\nExemplos de previsão (limpos):")
    for d in [0, 2, 4, 6, 8, 9]:
        x = X[d]
        yb = model.predict_binary(x)
        pred = bits_to_int(yb)
        print(f"Dígito {d} -> previsto {pred}")
        print(render_ascii(x))
        print()

    print("Exemplos com ruído:")
    for d in [1, 3, 5, 7]:
        x = add_noise(X[d], drop_prob=0.25, spur_prob=0.05)
        yb = model.predict_binary(x)
        pred = bits_to_int(yb)
        print(f"Base {d} (ruído) -> previsto {pred}")
        print(render_ascii(x))
        print()


def main() -> None:
    print("Backpropagation do zero (Python puro, sigmoid + MSE)")
    print("Regra de aprendizado: w := w - lr * dE/dw")
    run_xor()
    run_seven_segment()


if __name__ == "__main__":
    main()
