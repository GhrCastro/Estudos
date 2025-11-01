import random
from typing import Dict, List, Tuple


# Mapeamento dos dígitos para segmentos (a..g)
# 1 = ligado, 0 = desligado
SEGMENTS: Dict[int, List[int]] = {
    0: [1, 1, 1, 1, 1, 1, 0],
    1: [0, 1, 1, 0, 0, 0, 0],
    2: [1, 1, 0, 1, 1, 0, 1],
    3: [1, 1, 1, 1, 0, 0, 1],
    4: [0, 1, 1, 0, 0, 1, 1],
    5: [1, 0, 1, 1, 0, 1, 1],
    6: [1, 0, 1, 1, 1, 1, 1],
    7: [1, 1, 1, 0, 0, 0, 0],
    8: [1, 1, 1, 1, 1, 1, 1],
    9: [1, 1, 1, 1, 0, 1, 1],
}


def int_to_bits(n: int, width: int = 4) -> List[int]:
    return [(n >> (width - 1 - i)) & 1 for i in range(width)]


def bits_to_int(bits: List[int]) -> int:
    n = 0
    for b in bits:
        n = (n << 1) | (1 if b else 0)
    return n


def dataset_binary_coded_digits() -> Tuple[List[List[float]], List[List[float]]]:
    """Retorna (X, T) com 10 amostras (0..9).
    - X: 7 features (segmentos a..g)
    - T: 4 saídas (binário de 0..9)
    """
    X: List[List[float]] = []
    T: List[List[float]] = []
    for d in range(10):
        X.append([float(v) for v in SEGMENTS[d]])
        T.append([float(v) for v in int_to_bits(d, 4)])
    return X, T


def add_noise(vec: List[float], drop_prob: float = 0.2, spur_prob: float = 0.05) -> List[float]:
    """Adiciona ruído simulando falha de segmento (apaga alguns 1s) e ruído espúrio (acende alguns 0s)."""
    out: List[float] = []
    for v in vec:
        r = random.random()
        if v >= 0.5:  # ligado
            if r < drop_prob:
                out.append(0.0)
            else:
                out.append(1.0)
        else:  # desligado
            if r < spur_prob:
                out.append(1.0)
            else:
                out.append(0.0)
    return out


def render_ascii(segments: List[float]) -> str:
    """Desenha os 7 segmentos como ASCII, retornando 3 linhas.
    Ordem: a, b, c, d, e, f, g
    """
    a, b, c, d, e, f, g = [1 if v >= 0.5 else 0 for v in segments]
    top = " " + ("—" * 3 if a else "   ") + " "
    mid = ("|" if f else " ") + ("—" * 3 if g else "   ") + ("|" if b else " ")
    bot = ("|" if e else " ") + ("—" * 3 if d else "   ") + ("|" if c else " ")
    return "\n".join([top, mid, bot])

