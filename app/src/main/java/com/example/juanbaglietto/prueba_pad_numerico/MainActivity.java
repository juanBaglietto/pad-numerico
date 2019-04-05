package com.example.juanbaglietto.prueba_pad_numerico;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText mNumerIn;
    private EditText mNumerOrginal;
    private TextView mTexOut;
    private float mNumerSave;
    InputMethodManager keyboard;
    TextWatcher tt = null;
    private float TextSizeUp;
    private float TextSizeDown;
    private Contador mTimeOut;
    private float cont=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumerIn= (EditText) findViewById(R.id.editText2);
        mNumerOrginal= (EditText) findViewById(R.id.editText3);
        mTexOut=(TextView) findViewById(R.id.textView);
        keyboard = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); // se crea un objeto del tipo teclado para despues poder mostrarlo

        mTimeOut= new Contador(1000,1000);
        mTimeOut.start();

        TextSizeDown = (float) mNumerIn.getTextSize();      // cargo el tamanio mas chico de letra con el valor definido en el xml
        TextSizeUp = TextSizeDown +5;                       // defino el tamanio mas grande de letra a partir del tamnio definido en el xml

        /*Funcion que se llama cuando se detecta un long press en el texEdit mNumerIn */
        mNumerIn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(), "long Press", Toast.LENGTH_SHORT).show();
                keyboard.showSoftInput(mNumerIn, 0);      //
                return true;
            }
        });


/*Funcion que se llama cuando se apreta sobre el boton Ok o FIN del teclado */
        mNumerIn.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    //comtemplar si cumple con el formato xxx.xx
                    if(mNumerIn.getText().toString().isEmpty())
                    {
                        Toast.makeText(getApplicationContext(), "No ingreso nada", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if ((Float.valueOf(mNumerIn.getText().toString()) < 1000)) {
                            /*Lugar para llamar a la fucion que edite el numero en la app*/
                            mNumerSave = Float.valueOf(mNumerIn.getText().toString());
                            Toast.makeText(getApplicationContext(), "Numero Guardado:" + mNumerSave, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Nuemro ingresado mayor a 1000", Toast.LENGTH_SHORT).show();
                        }
                    }

                    keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);    //esconde el taclado


                    /*Una vez que el usuario termino de editar el campo cargo el nuevo valor ingresado en la varible que se actualiza cada 1 segundo
                    * Despues actulizo el valor que se muestra en el textedit mNumerOrginal con el nuevo valor ingresado  */
                    cont=mNumerSave;
                    mNumerOrginal.setText(""+cont);


                    mNumerIn.setTextSize(TypedValue.COMPLEX_UNIT_SP,TextSizeDown); // esta funcion reduce el tamanio de la letra al valor inicial del xml

                    return true;
                }
                return false;
            }
        });


            /* La clese TextWatcher perminte reconocer las acciones que se producen cuando el usuario esta editando un textEdit   */
        mNumerIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                /*Se entra aca cuando el usurio empieza a ingresar un nuevo numero pero antes que este se muestre en el textEdit*/

                mNumerIn.setTextSize(TypedValue.COMPLEX_UNIT_SP,TextSizeUp);  // Funcion que cambia el tamanio de los numeros

            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

     /*Clase que utilizo para generar la interucion del timer cada 1 segundo */
    public class Contador extends CountDownTimer {

        public Contador(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            cont++;
            mNumerOrginal.setText(""+cont);
            if(cont>=1000)
            {
                cont=0;
            }
            mTimeOut.start();

        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

    }

}
