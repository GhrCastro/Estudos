using Microsoft.EntityFrameworkCore;
using ProjetoPedidos.Models;

namespace ProjetoPedidos.Data;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
    {
    }

    public DbSet<Pedido> Pedidos => Set<Pedido>();
    public DbSet<Item> Itens => Set<Item>();
    public DbSet<Produto> Produtos => Set<Produto>();
    public DbSet<ProdutoEletronico> ProdutosEletronicos => Set<ProdutoEletronico>();
    public DbSet<ProdutoPerecivel> ProdutosPereciveis => Set<ProdutoPerecivel>();

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Pedido>(entity =>
        {
            entity.ToTable("Pedidos");
            entity.HasKey(p => p.Id);
            entity.Property(p => p.ValorTotal).HasPrecision(10, 2);
            entity.Property(p => p.Data).HasColumnType("datetime");

            entity.HasMany(p => p.Itens)
                .WithOne(i => i.Pedido)
                .HasForeignKey(i => i.PedidoId)
                .OnDelete(DeleteBehavior.Cascade);
        });

        modelBuilder.Entity<Item>(entity =>
        {
            entity.ToTable("Itens");
            entity.HasKey(i => i.CodigoItem);
            entity.Property(i => i.ValorItem).HasPrecision(10, 2);

            entity.HasOne(i => i.Produto)
                .WithMany(p => p.Itens)
                .HasForeignKey(i => i.ProdutoId)
                .OnDelete(DeleteBehavior.Restrict);
        });

        modelBuilder.Entity<Produto>(entity =>
        {
            entity.ToTable("Produtos");
            entity.HasKey(p => p.Id);
            entity.Property(p => p.Nome).HasMaxLength(120).IsRequired();
            entity.Property(p => p.Preco).HasPrecision(10, 2);

            entity.HasDiscriminator<string>("TipoProduto")
                .HasValue<Produto>("Comum")
                .HasValue<ProdutoEletronico>("Eletronico")
                .HasValue<ProdutoPerecivel>("Perecivel");
        });

        modelBuilder.Entity<ProdutoEletronico>(entity =>
        {
            entity.Property(p => p.Voltagem).HasColumnName("Voltagem");
        });

        modelBuilder.Entity<ProdutoPerecivel>(entity =>
        {
            entity.Property(p => p.DataValidade).HasColumnType("datetime");
        });
    }
}
