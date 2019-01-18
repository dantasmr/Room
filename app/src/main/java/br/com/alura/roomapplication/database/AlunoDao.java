package br.com.alura.roomapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.roomapplication.models.Aluno;

@Dao
public interface AlunoDao {

    @Insert
    void insere(Aluno aluno);

    @Query("select * from aluno")
    List<Aluno> busca();

    @Update
    void altera(Aluno aluno);

    @Delete
    void deleta(Aluno aluno);

    @Query("select * from aluno where nome like :nomealuno")
    List<Aluno> getAlunos(String nomealuno);

}
