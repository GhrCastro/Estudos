namespace ProjetoPedidos.Models;

public class Item
{
    public int CodigoItem { get; set; }
    public int Quantidade { get; set; }
    public decimal ValorItem { get; set; }

    public int PedidoId { get; set; }
    public Pedido? Pedido { get; set; }

    public int ProdutoId { get; set; }
    public Produto? Produto { get; set; }
}
