package com.agencia.painel_admin.repository;

import java.util.List;
import org.springframework.data.repository.query.Param; // Import importante!
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.agencia.painel_admin.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Contagem geral 
    @Query(value = """
        SELECT MONTH(created_at) AS mes, COUNT(*) AS total
        FROM cliente
        GROUP BY MONTH(created_at)
        ORDER BY mes
    """, nativeQuery = true)
    List<Object[]> contarClientesPorMes();

    // Contagem filtrada por ANO
    @Query(value = """
        SELECT MONTH(created_at) AS mes, COUNT(*) AS total
        FROM cliente
        WHERE YEAR(created_at) = :ano
        GROUP BY MONTH(created_at)
        ORDER BY mes
    """, nativeQuery = true)
    List<Object[]> contarClientesPorMesPorAno(@Param("ano") int ano);

    // Receita por ANO 
    @Query(value = """
        SELECT MONTH(created_at) AS mes, SUM(valor) AS total
        FROM financeiro
        WHERE tipo IN ('ENTRADA', 'RECEITA') AND YEAR(created_at) = :ano
        GROUP BY MONTH(created_at)
        ORDER BY mes
    """, nativeQuery = true)
    List<Object[]> receitaPorAno(@Param("ano") int ano);
}