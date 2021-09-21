package bo.edu.ucb.est;

public class Usuario {
    Long idUser;
    int iteracion;
    double[] variables = {0,0};

    public Usuario(Long idUser) {
        this.idUser = idUser;
        this.iteracion = 1;
    }

    public Long getIdUser() {
        return idUser;
    }

    public int getIteracion() {
        return iteracion;
    }

    public void setIteracion(int iteracion) {
        this.iteracion = iteracion;
    }

    public double[] getVariables() {
        return variables;
    }

    public void setVariables(double[] variables) {
        this.variables = variables;
    }
}
