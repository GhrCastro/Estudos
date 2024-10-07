Oi Theldo, Gustavo aqui, primeiramente bom dia/ boa tarde/ boa noite, dependendo de quando você estiver lendo, vou colocar sempre estes 'Readme's' com estes cumprimentos como padrão :)

-Guia 02-

-Desde já peço perdão, não consegui tempo essa semana devido a algumas correrias e o dia dos pais, então acabei não conseguindo fazer o extra da 4 de java, e nem a questão 5 ;-; prometo o dobro do esforço na guia da semana que vem.

-SOBRE A QUESTÃO 02b-
   Nessa questão fiquei horas tentando entender algumas coisas que o verilog me deixou maluco:
   A primeira delas foi o fato de os registradores ao converter a parte inteira separadamente da fracionária, invertiam sempre a parte inteira, até entender que era por que ao converter de decimal para binário colocamos os bits do último ao primeiro, perdi muitas horas.
   A segunda delas é que aparentemente o verilog tem o péssimo hábito de arredondar grandezas fracionárias específicas quando se retira ou se inclui uma parte inteira de um real, então as letras C e E deste exercício ficavam tendo discrepâncias absurdas, mas enfim consegui dar uma pesquisada e utilizei $floor, para extrair o inteiro, impedindo que qualquer arredondamento ocorresse(Mais uma vez aeds 2 salvando com os conceitos de teto e piso).

-SOBRE OS EXTRAS-
   Sobre o código de java, note que ele funciona de acordo com o usuário e os números binários que ele quiser inserir.