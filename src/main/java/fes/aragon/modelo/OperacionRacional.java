package fes.aragon.modelo;

public class OperacionRacional {  //Se declaran variables = objetos creados
    private Racional racionalUno;
    private Racional racionalDos;
    //AÚN NO ESTÁN INICIALIZADAS. Su valor es null

    public OperacionRacional(){
        this.racionalUno = new Racional(); //new: palabra reservada para inicializar objetos
        this.racionalDos = new Racional();
    }
    public OperacionRacional(Racional r1, Racional r2){ //renombramos variables
        this.racionalUno = r1; //El uso de "this." no es necesario e igualmente funciona
        racionalDos = r2;
    }

    public Racional suma(){ //Empieza "back en" (esqueleto)
        int numerador = this.racionalUno.getNumerador()*racionalDos.getDenominador() +
                racionalUno.getDenominador()*racionalDos.getNumerador();
        int denominador = racionalUno.getDenominador()*racionalDos.getDenominador();
        return new Racional(numerador, denominador); //"Devuelve un racional" un nuevo objeto compuesto por()
    }
    public Racional suma(Racional r1, Racional r2){
        int numerador = r1.getNumerador()* r2.getDenominador() + r1.getDenominador()*r2.getNumerador();
        int denominador = r1.getDenominador()*r2.getDenominador();
        return new Racional(numerador, denominador);
    }

    public Racional resta(){
        int numerador = this.racionalUno.getNumerador()*racionalDos.getDenominador() -
                racionalUno.getDenominador()*racionalDos.getNumerador();
        int denominador = racionalUno.getDenominador()*racionalDos.getDenominador();
        return new Racional(numerador, denominador);
    }

    public Racional resta(Racional r1, Racional r2){
        int numerador = r1.getNumerador()* r2.getDenominador() - r1.getDenominador()*r2.getNumerador();
        int denominador = r1.getDenominador()* r2.getDenominador();
        return new Racional(numerador,denominador);
    }

    public Racional multiplicacion(){
        int numerador = racionalUno.getNumerador()* racionalDos.getNumerador();
        int denominador = racionalUno.getDenominador()*racionalDos.getDenominador();
        return new Racional(numerador,denominador);
    }

    public Racional multiplicacion(Racional r1, Racional r2){
        int numerador = r1.getNumerador()* r2.getNumerador();
        int denominador = r1.getDenominador()*r2.getDenominador();
        return new Racional(numerador,denominador);
    }

    public Racional division(){
        int numerador = racionalUno.getNumerador()* racionalDos.getDenominador();
        int denominador = racionalUno.getDenominador()*racionalDos.getNumerador();
        return new Racional(numerador,denominador);
    }

    public Racional division(Racional r1, Racional r2){
        int numerador = r1.getNumerador()* r2.getDenominador();
        int denominador = r1.getDenominador()*r2.getNumerador();
        return  new Racional(numerador,denominador);
    }

    public Racional getRacionalUno() {
        return racionalUno;
    }

    public void setRacionalUno(Racional racionalUno) {
        this.racionalUno = racionalUno;
    }

    public Racional getRacionalDos() {
        return racionalDos;
    }

    public void setRacionalDos(Racional racionalDos) {
        this.racionalDos = racionalDos;
    }
}
