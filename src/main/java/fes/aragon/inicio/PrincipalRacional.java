package fes.aragon.inicio;

import fes.aragon.modelo.OperacionRacional;
import fes.aragon.modelo.Racional;

public class PrincipalRacional {
    public static void main(String[] args) {
        Racional r1 = new Racional(2,3);
        Racional r2 = new Racional(1,2);
        OperacionRacional op = new OperacionRacional(r1,r2);

        //Suma
        Racional rs = op.suma();
        System.out.println(rs.getNumerador()+"/"+rs.getDenominador());

        //Resta
        Racional rr = op.resta(r1,r2);
        System.out.println(rr.getNumerador()+"/"+ rr.getDenominador());

        //Multiplicación
        Racional rm = op.multiplicacion();
        System.out.println(rm.getNumerador()+"/"+rm.getDenominador());

        //División
        Racional rd = op.division();
        System.out.println(rd.getNumerador()+"/"+rd.getDenominador());


    }
}
