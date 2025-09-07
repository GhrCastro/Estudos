# 1. IMPORTAÇÃO DE BIBLIOTECAS
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.preprocessing import LabelEncoder, OneHotEncoder
from sklearn.compose import ColumnTransformer
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier, plot_tree, export_text
from sklearn.metrics import accuracy_score, confusion_matrix, classification_report
from yellowbrick.classifier import ConfusionMatrix
import os
os.environ["PATH"] += os.pathsep + r"C:\Program Files\Graphviz\bin"


# 2. LER A BASE DE DADOS
train = pd.read_csv('Estudos/I.A/train.csv')
test = pd.read_csv('Estudos/I.A/test.csv')

print("\nVisualizando as primeiras linhas da base de treino:\n")
print(train.head())

print("\nInformações gerais da base:\n")
print(train.info())

# 3. DISTRIBUIÇÃO DA CLASSE
sns.countplot(x='Survived', data=train, palette=['#FF6F61','#6B5B95'])
plt.title("Distribuição da variável Survived")
plt.show()

# 4. TRATAMENTO DE VALORES AUSENTES
train['Age'].fillna(train['Age'].median(), inplace=True)
train['Embarked'].fillna(train['Embarked'].mode()[0], inplace=True)
test['Age'].fillna(test['Age'].median(), inplace=True)
test['Fare'].fillna(test['Fare'].median(), inplace=True)

# 5. SEPARAÇÃO DE ATRIBUTOS E CLASSE
X_train_base = train.drop(['Survived','Name','Ticket','Cabin','PassengerId'], axis=1)
y_train = train['Survived']
X_test_base = test.drop(['Name','Ticket','Cabin','PassengerId'], axis=1)

# 6. CODIFICAÇÃO DE ATRIBUTOS CATEGÓRICOS
# Sex
X_train_base['Sex'] = LabelEncoder().fit_transform(X_train_base['Sex'])
X_test_base['Sex'] = LabelEncoder().fit_transform(X_test_base['Sex'])

# Embarked
onehot = ColumnTransformer(transformers=[('OneHot', OneHotEncoder(), ['Embarked'])], remainder='passthrough')
X_train = onehot.fit_transform(X_train_base)
X_test = onehot.transform(X_test_base)

# 7. TREINAMENTO DO MODELO
modelo = DecisionTreeClassifier(criterion='entropy', random_state=0)
modelo.fit(X_train, y_train)

# 8. AVALIAÇÃO DO MODELO
y_pred_train = modelo.predict(X_train)

print("\nAcurácia no treino:", accuracy_score(y_train, y_pred_train))
print("\nMatriz de Confusão:")
print(confusion_matrix(y_train, y_pred_train))
print("\nRelatório de Classificação:")
print(classification_report(y_train, y_pred_train))

# 9. MATRIZ COM YELLOWBRICK
cm = ConfusionMatrix(modelo, classes=['Nao', 'Sim'])
cm.fit(X_train, y_train)
cm.score(X_train, y_train)
cm.show()

# 10. VISUALIZAÇÃO DA ÁRVORE
plt.figure(figsize=(20,12))
plot_tree(modelo, filled=True, feature_names=onehot.get_feature_names_out(), class_names=['Nao','Sim'])
plt.show()

# 11. EXTRAÇÃO DE REGRAS
rules = export_text(modelo, feature_names=list(onehot.get_feature_names_out()))
print("\nRegras de decisão da árvore:\n")
print(rules)

from sklearn.tree import export_graphviz
import graphviz

# Gerar árvore no formato DOT
dot_data = export_graphviz(
    modelo,
    out_file=None,                        # não salva em arquivo ainda
    feature_names=onehot.get_feature_names_out(),
    class_names=['Nao','Sim'],
    filled=True,
    rounded=True,
    special_characters=True
)

# Criar objeto Graphviz
graph = graphviz.Source(dot_data)

# Exibir interativo (abre no visualizador padrão do sistema)
graph.view("arvore_titanic")  # gera arquivo PDF e abre automaticamente

# Opcional: visualizar dentro do Jupyter Notebook (se usar Jupyter)
# graph
