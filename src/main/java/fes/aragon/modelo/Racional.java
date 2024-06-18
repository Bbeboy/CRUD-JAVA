package fes.aragon.modelo;

public class Racional {
    private int numerador;
    private int denominador;

    public Racional() {
        this.numerador=0;
        this.denominador=1;
    }

    public Racional(int numerador, int denominador) {
        this.numerador = numerador;
        this.setDenominador(denominador);
    }

    public int getNumerador() {
        return numerador;
    }

    public void setNumerador(int numerador) {
        this.numerador = numerador;
    }
    public int getDenominador() {
        return this.denominador;
    }

    public void setDenominador(int denominador) {
        if(denominador==0){
            this.denominador=1;
        }else {
            this.denominador = denominador;
        }
    }
}

