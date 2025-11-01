import math
import random
from typing import List, Tuple


def sigmoid(x: float) -> float:
    # Numeric guard for large magnitude inputs
    if x < -60:
        return 0.0
    if x > 60:
        return 1.0
    return 1.0 / (1.0 + math.exp(-x))


class MLP:
    """
    MLP de 2 camadas (entrada->oculta->saída) com sigmoid e MSE.
    Implementação minimalista, sem NumPy.
    """

    def __init__(self, n_inputs: int, n_hidden: int, n_outputs: int, seed: int | None = None):
        if seed is not None:
            random.seed(seed)

        scale = 0.5
        # Pesos: matrizes em formato [entrada][saida]
        self.W1 = [[(random.random() * 2 - 1) * scale for _ in range(n_hidden)] for _ in range(n_inputs)]
        self.b1 = [(random.random() * 2 - 1) * scale for _ in range(n_hidden)]
        self.W2 = [[(random.random() * 2 - 1) * scale for _ in range(n_outputs)] for _ in range(n_hidden)]
        self.b2 = [(random.random() * 2 - 1) * scale for _ in range(n_outputs)]

        # caches da última passagem
        self._last_x: List[float] | None = None
        self._z1: List[float] | None = None
        self._h: List[float] | None = None
        self._z2: List[float] | None = None
        self._y: List[float] | None = None

    # utilidades de álgebra linear (com listas)
    @staticmethod
    def dot(vec: List[float], mat: List[List[float]]) -> List[float]:
        # mat é [len(vec)][n_out]
        n_out = len(mat[0])
        out = [0.0] * n_out
        for j in range(n_out):
            s = 0.0
            for i, v in enumerate(vec):
                s += v * mat[i][j]
            out[j] = s
        return out

    @staticmethod
    def add_bias(vec: List[float], bias: List[float]) -> List[float]:
        return [v + b for v, b in zip(vec, bias)]

    @staticmethod
    def apply_sigmoid(vec: List[float]) -> List[float]:
        return [sigmoid(v) for v in vec]

    def forward(self, x: List[float]) -> List[float]:
        # Camada oculta
        z1 = self.add_bias(self.dot(x, self.W1), self.b1)
        h = self.apply_sigmoid(z1)
        # Saída
        z2 = self.add_bias(self.dot(h, self.W2), self.b2)
        y = self.apply_sigmoid(z2)

        # guarda caches
        self._last_x, self._z1, self._h, self._z2, self._y = x, z1, h, z2, y
        return y

    @staticmethod
    def mse(y: List[float], t: List[float]) -> float:
        return 0.5 * sum((yi - ti) ** 2 for yi, ti in zip(y, t))

    def backward(self, t: List[float], lr: float) -> None:
        # Assume que forward() foi chamado e caches preenchidos
        assert self._last_x is not None and self._z1 is not None and self._h is not None
        assert self._z2 is not None and self._y is not None

        x = self._last_x
        z1 = self._z1
        h = self._h
        z2 = self._z2
        y = self._y

        # Erro na saída: dE/dy = (y - t)  (pela MSE 0.5*sum(...))
        dE_dy = [y[k] - t[k] for k in range(len(y))]
        # dy/dz2 = sigmoid'(z2) = y*(1-y)
        dy_dz2 = [y[k] * (1.0 - y[k]) for k in range(len(y))]
        # Cadeia: dE/dz2
        delta2 = [dE_dy[k] * dy_dz2[k] for k in range(len(y))]

        # Gradientes para W2 e b2
        for j in range(len(h)):
            for k in range(len(y)):
                grad = h[j] * delta2[k]
                self.W2[j][k] -= lr * grad
        for k in range(len(y)):
            self.b2[k] -= lr * delta2[k]

        # Backprop para camada oculta
        # dE/dh_j = sum_k delta2_k * W2_jk
        dE_dh = []
        for j in range(len(h)):
            s = 0.0
            for k in range(len(y)):
                s += delta2[k] * self.W2[j][k]
            dE_dh.append(s)
        # dh/dz1 = sigmoid'(z1) = h*(1-h)
        dh_dz1 = [h[j] * (1.0 - h[j]) for j in range(len(h))]
        delta1 = [dE_dh[j] * dh_dz1[j] for j in range(len(h))]

        # Gradientes para W1 e b1
        for i in range(len(x)):
            for j in range(len(h)):
                grad = x[i] * delta1[j]
                self.W1[i][j] -= lr * grad
        for j in range(len(h)):
            self.b1[j] -= lr * delta1[j]

    def train(
        self,
        X: List[List[float]],
        T: List[List[float]],
        epochs: int = 1000,
        lr: float = 0.3,
        shuffle: bool = True,
        verbose_every: int | None = 100,
    ) -> List[float]:
        losses: List[float] = []
        data = list(zip(X, T))
        for epoch in range(1, epochs + 1):
            if shuffle:
                random.shuffle(data)
            total = 0.0
            for x, t in data:
                y = self.forward(x)
                total += self.mse(y, t)
                self.backward(t, lr)
            avg = total / len(data)
            losses.append(avg)
            if verbose_every and epoch % verbose_every == 0:
                print(f"[epoch {epoch}] loss={avg:.6f}")
        return losses

    def predict(self, x: List[float]) -> List[float]:
        return self.forward(x)

    def predict_binary(self, x: List[float], thr: float = 0.5) -> List[int]:
        return [1 if v >= thr else 0 for v in self.predict(x)]

