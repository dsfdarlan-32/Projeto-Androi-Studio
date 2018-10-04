package com.example.darlan.cadastraaluno;

import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editCodido, editNome, editEmail;
    Button btnSalvar, btnExcluir, btnLimpar;
    ListView listViewAluno;

    BancoDados db = new BancoDados(this);

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    InputMethodManager imm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editCodido = (EditText) findViewById(R.id.editCodigo);
        editNome = (EditText) findViewById(R.id.editNome);
        editEmail = (EditText) findViewById(R.id.editEmail);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnExcluir = (Button) findViewById(R.id.btnExcluir);
        btnLimpar = (Button) findViewById(R.id.btnLimpar);

        imm = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);

        listViewAluno = (ListView) findViewById(R.id.listViewAluno);

        listarAlunos();

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpaCampos();
            }
        });



        listViewAluno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String conteudo = (String) listViewAluno.getItemAtPosition(position);

                //Toast.makeText(MainActivity.this, "Select: " + conteudo, Toast.LENGTH_LONG).show();

                String codigo = conteudo.substring(0,conteudo.indexOf("-"));

                Aluno aluno = db.selecinarAluno(Integer.parseInt(codigo));

                editCodido.setText(String.valueOf(aluno.getCodigo()) );
                editNome.setText(aluno.getNome());
                editEmail.setText(aluno.getEmail());
            }
        });


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codigo = editCodido.getText().toString();
                String nome = editNome.getText().toString();
                String email = editEmail.getText().toString();

                if(nome.isEmpty()){
                    editNome.setError("Este campo e obrigatorio");

                } else if (codigo.isEmpty()){
                        //insert
                    db.addAluno( new Aluno(nome,email));

                    Toast.makeText(MainActivity.this,"Salvo com sucesso",Toast.LENGTH_LONG).show();

                    limpaCampos();
                    listarAlunos();
                    escondeTeclado();
                }else {
                    //upadate

                    db.atualizarAluno(new Aluno(Integer.parseInt(codigo),nome,email));

                    Toast.makeText(MainActivity.this,"Atualizado com sucesso",Toast.LENGTH_LONG).show();

                    limpaCampos();
                    listarAlunos();
                    escondeTeclado();

                }

            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codigo = editCodido.getText().toString();

                if(codigo.isEmpty()){
                    Toast.makeText(MainActivity.this, "Nenhum aluno Estar Selecionado",Toast.LENGTH_LONG).show();
                }else {

                    Aluno aluno= new Aluno();
                    aluno.setCodigo(Integer.parseInt(codigo));
                    db.apagarAluno(aluno);

                    Toast.makeText(MainActivity.this, "Aluno excluido com sucesso",Toast.LENGTH_LONG).show();

                    limpaCampos();
                    listarAlunos();
                    escondeTeclado();
                }

            }
        });
    }


    void escondeTeclado(){
        imm.hideSoftInputFromWindow(editNome.getWindowToken(),0);
    }

    void limpaCampos(){
        editCodido.setText("");
        editNome.setText("");
        editEmail.setText("");

        editNome.requestFocus();
    }



    public void listarAlunos() {
        List<Aluno> alunos = db.listaTodosAlunos();

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList );

        listViewAluno.setAdapter(adapter);



        for (Aluno c : alunos) {
            //Log.d("Lista", "\nID: " + c.getCodigo() + "nome: " + c.getNome());

            arrayList.add(c.getCodigo() + "-" + c.getNome());

            adapter.notifyDataSetChanged();

        }
    }

}