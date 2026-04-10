package com.agencia.painel_admin.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; 
import org.springframework.stereotype.Repository;
import com.agencia.painel_admin.model.Financeiro;

@Repository
public interface FinanceiroRepository extends JpaRepository<Financeiro, Long> {

    // Calcula o lucro total 
    @Query(value = """
        SELECT 
         COALESCE(SUM(CASE WHEN tipo IN ('ENTRADA', 'RECEITA') THEN valor ELSE 0 END), 0) - 
         COALESCE(SUM(CASE WHEN tipo IN ('SAIDA', 'DESPESA', 'SAÍDA') THEN valor ELSE 0 END), 0) 
        FROM financeiro
    """, nativeQuery = true)
    Double calcularLucroTotal();

    // Soma apenas as Entradas 
    @Query(value = """
        SELECT COALESCE(SUM(valor), 0) 
        FROM financeiro 
        WHERE tipo IN ('ENTRADA', 'RECEITA')
    """, nativeQuery = true)
    Double somarEntradas();

    // Busca receita por mês geral
    @Query(value = """
        SELECT MONTH(created_at) as mes, SUM(valor) as total 
        FROM financeiro 
        WHERE tipo IN ('ENTRADA', 'RECEITA') 
        AND created_at IS NOT NULL
        GROUP BY MONTH(created_at)
        ORDER BY mes
    """, nativeQuery = true)
    List<Object[]> receitaPorMes();

    // Receita filtrada por ANO 
    @Query(value = """
       SELECT MONTH(created_at) AS mes, SUM(valor) AS total
       FROM financeiro
       WHERE tipo IN ('ENTRADA', 'RECEITA') AND YEAR(created_at) = :ano
       GROUP BY MONTH(created_at)
       ORDER BY mes
    """, nativeQuery = true)
    List<Object[]> receitaPorAno(@Param("ano") int ano);
}