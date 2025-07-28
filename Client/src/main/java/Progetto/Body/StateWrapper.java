package Progetto.Body;

public class StateWrapper {
    public StateMenu stato;
    public String codiceFiscale;
    public Roles ruolo;

    public StateWrapper(StateMenu stato, String codiceFiscale, Roles ruolo) {
        this.stato = stato;
        this.codiceFiscale = codiceFiscale;
        this.ruolo = ruolo;
    }
}
