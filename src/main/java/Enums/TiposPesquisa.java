package Enums;

public enum TiposPesquisa {
    NORMAL, COM_LIMITE_MAXIMO, COM_LIMITE_GRAU;

    @Override
    public String toString() {
        switch (this){
            case NORMAL:
                return "Pesquisa normal";
            case COM_LIMITE_GRAU:
                return "Limitar por grau";
            case COM_LIMITE_MAXIMO:
                return "Limitar por quantidade";
            default:
                return "Erro na escolha do tipo";
        }
    }
}

