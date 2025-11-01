import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.preprocessing import LabelEncoder, OneHotEncoder
from sklearn.compose import ColumnTransformer
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier, plot_tree
from sklearn.metrics import accuracy_score, confusion_matrix, classification_report
from yellowbrick.classifier import ConfusionMatrix

# 1. LER A BASE
base = pd.read_csv('Estudos/I.A/restaurante (1).csv', sep=';')

print("\nBase inicial:\n")
print(base)

# 2. DISTRIBUIÇÃO DA CLASSE
sns.countplot(x='Conclusao', data=base, palette=['#A0C49D', '#0173B2'])
plt.title("Distribuição da variável Conclusao")
plt.show()

# 3. SEPARAR ATRIBUTOS E CLASSE
X = base.iloc[:, 0:10].copy()  # Atributos
y = base['Conclusao']          # Classe

# 4. CODIFICAÇÃO MANUAL PARA 'Cliente'
mapeamento_cliente = {'Nenhum': 0, 'Alguns': 1, 'Cheio': 2}
X['Cliente'] = X['Cliente'].map(mapeamento_cliente)

# 5. CODIFICAÇÃO MANUAL PARA 'Preco'
map_preco = {'R': 0, 'RR': 1, 'RRR': 2}
X['Preco'] = X['Preco'].map(map_preco)

# 6. LABEL ENCODER PARA ATRIBUTOS CATEGÓRICOS RESTANTES
label_cols = ['Alternativo', 'Bar', 'SexSab', 'fome', 'Chuva', 'Res', 'Tempo']
for col in label_cols:
    X[col] = LabelEncoder().fit_transform(X[col])

# 7. ONE-HOT ENCODER PARA 'Tipo'
onehot = ColumnTransformer(transformers=[('OneHot', OneHotEncoder(), [8])], remainder='passthrough')
X_encoded = onehot.fit_transform(X)

# 8. DIVISÃO TREINO/TESTE
X_train, X_test, y_train, y_test = train_test_split(X_encoded, y, test_size=0.2, random_state=23)

# 9. TREINAR MODELO
modelo = DecisionTreeClassifier(criterion='entropy', random_state=0)
modelo.fit(X_train, y_train)

# 10. AVALIAÇÃO
y_pred = modelo.predict(X_test)

print("\nAcurácia:", accuracy_score(y_test, y_pred))
print("\nMatriz de Confusão:")
print(confusion_matrix(y_test, y_pred))
print("\nRelatório de Classificação:")
print(classification_report(y_test, y_pred))

# 11. MATRIZ COM YELLOWBRICK
cm = ConfusionMatrix(modelo, classes=['Nao', 'Sim'])
cm.fit(X_train, y_train)
cm.score(X_test, y_test)
cm.show()

# 12. VISUALIZAÇÃO DA ÁRVORE (adaptada)
plt.figure(figsize=(22,12))
plot_tree(
    modelo,
    filled=True,                    # Preenche os nós com cores
    feature_names=onehot.get_feature_names_out(),  # Nomes das features
    class_names=['Nao', 'Sim'],     # Classes
    rounded=True,                   # Nós arredondados
    fontsize=12                     # Tamanho da fonte
)
plt.show()
