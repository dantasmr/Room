package br.com.alura.roomapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import br.com.alura.roomapplication.database.conversor.ConversorData;
import br.com.alura.roomapplication.models.Aluno;

@Database(entities= {Aluno.class}, version=2)
@TypeConverters(ConversorData.class)
public abstract class AluraDatabase extends RoomDatabase {

    public abstract AlunoDao getAlunoDao();


}
