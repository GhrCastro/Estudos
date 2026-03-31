namespace ProjetoPedidos.DTOs;

public class ItemCreateDto
{
    public int PedidoId { get; set; }
    public int ProdutoId { get; set; }
    public int Quantidade { get; set; }
}
