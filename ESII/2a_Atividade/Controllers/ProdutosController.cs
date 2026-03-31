using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ProjetoPedidos.Data;
using ProjetoPedidos.DTOs;
using ProjetoPedidos.Models;

namespace ProjetoPedidos.Controllers;

[ApiController]
[Route("api/[controller]")]
public class ProdutosController : ControllerBase
{
    private readonly AppDbContext _context;

    public ProdutosController(AppDbContext context)
    {
        _context = context;
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<object>>> GetAll()
    {
        var produtos = await _context.Produtos.AsNoTracking().ToListAsync();
        return Ok(produtos.Select(MapProduto));
    }

    [HttpGet("{id:int}")]
    public async Task<ActionResult<object>> GetById(int id)
    {
        var produto = await _context.Produtos.FindAsync(id);
        if (produto is null)
        {
            return NotFound();
        }

        return Ok(MapProduto(produto));
    }

    [HttpPost]
    public async Task<ActionResult<object>> CreateComum([FromBody] ProdutoCreateDto dto)
    {
        if (!DadosBasicosValidos(dto.Nome, dto.Preco, dto.Estoque))
        {
            return BadRequest("Dados de produto invalidos.");
        }

        var produto = new Produto
        {
            Nome = dto.Nome,
            Preco = dto.Preco,
            Estoque = dto.Estoque
        };

        _context.Produtos.Add(produto);
        await _context.SaveChangesAsync();

        return CreatedAtAction(nameof(GetById), new { id = produto.Id }, MapProduto(produto));
    }

    [HttpPost("eletronicos")]
    public async Task<ActionResult<object>> CreateEletronico([FromBody] ProdutoEletronicoCreateDto dto)
    {
        if (!DadosBasicosValidos(dto.Nome, dto.Preco, dto.Estoque) || dto.Voltagem <= 0)
        {
            return BadRequest("Dados de produto eletronico invalidos.");
        }

        var produto = new ProdutoEletronico
        {
            Nome = dto.Nome,
            Preco = dto.Preco,
            Estoque = dto.Estoque,
            Voltagem = dto.Voltagem
        };

        _context.ProdutosEletronicos.Add(produto);
        await _context.SaveChangesAsync();

        return CreatedAtAction(nameof(GetById), new { id = produto.Id }, MapProduto(produto));
    }

    [HttpPost("pereciveis")]
    public async Task<ActionResult<object>> CreatePerecivel([FromBody] ProdutoPerecivelCreateDto dto)
    {
        if (!DadosBasicosValidos(dto.Nome, dto.Preco, dto.Estoque))
        {
            return BadRequest("Dados de produto perecivel invalidos.");
        }

        var produto = new ProdutoPerecivel
        {
            Nome = dto.Nome,
            Preco = dto.Preco,
            Estoque = dto.Estoque,
            DataValidade = dto.DataValidade
        };

        _context.ProdutosPereciveis.Add(produto);
        await _context.SaveChangesAsync();

        return CreatedAtAction(nameof(GetById), new { id = produto.Id }, MapProduto(produto));
    }

    [HttpPut("{id:int}")]
    public async Task<ActionResult<object>> Update(int id, [FromBody] ProdutoUpdateDto dto)
    {
        if (!DadosBasicosValidos(dto.Nome, dto.Preco, dto.Estoque))
        {
            return BadRequest("Dados de produto invalidos.");
        }

        var produto = await _context.Produtos.FindAsync(id);
        if (produto is null)
        {
            return NotFound();
        }

        produto.Nome = dto.Nome;
        produto.Preco = dto.Preco;
        produto.Estoque = dto.Estoque;

        if (produto is ProdutoEletronico eletronico)
        {
            if (dto.DataValidade.HasValue)
            {
                return BadRequest("Produto eletronico nao possui data de validade.");
            }

            if (dto.Voltagem.HasValue)
            {
                eletronico.Voltagem = dto.Voltagem.Value;
            }
        }

        if (produto is ProdutoPerecivel perecivel)
        {
            if (dto.Voltagem.HasValue)
            {
                return BadRequest("Produto perecivel nao possui voltagem.");
            }

            if (dto.DataValidade.HasValue)
            {
                perecivel.DataValidade = dto.DataValidade.Value;
            }
        }

        await _context.SaveChangesAsync();
        return Ok(MapProduto(produto));
    }

    [HttpDelete("{id:int}")]
    public async Task<IActionResult> Delete(int id)
    {
        var produto = await _context.Produtos.FindAsync(id);
        if (produto is null)
        {
            return NotFound();
        }

        var possuiItens = await _context.Itens.AnyAsync(i => i.ProdutoId == id);
        if (possuiItens)
        {
            return BadRequest("Nao e possivel excluir produto associado a itens de pedido.");
        }

        _context.Produtos.Remove(produto);
        await _context.SaveChangesAsync();

        return NoContent();
    }

    private static object MapProduto(Produto produto)
    {
        return produto switch
        {
            ProdutoEletronico eletronico => new
            {
                eletronico.Id,
                eletronico.Nome,
                eletronico.Preco,
                eletronico.Estoque,
                Tipo = "Eletronico",
                eletronico.Voltagem
            },
            ProdutoPerecivel perecivel => new
            {
                perecivel.Id,
                perecivel.Nome,
                perecivel.Preco,
                perecivel.Estoque,
                Tipo = "Perecivel",
                perecivel.DataValidade
            },
            _ => new
            {
                produto.Id,
                produto.Nome,
                produto.Preco,
                produto.Estoque,
                Tipo = "Comum"
            }
        };
    }

    private static bool DadosBasicosValidos(string nome, decimal preco, int estoque)
    {
        return !string.IsNullOrWhiteSpace(nome) && preco >= 0 && estoque >= 0;
    }
}
