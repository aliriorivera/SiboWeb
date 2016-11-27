/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

/**
 *
 * @author AnBoCa
 */
public class Paginador {
    private int npages;//numero de paginas
    private int nres;//numero de resultados por pagina
    private int pageact;//pagina actual
    private int nreg;//numero de registros de la consulta

    public Paginador(){
        npages=0;
        nres=6;
        pageact=0;
        nreg=0;
    }

    /**
     * @return the npages
     */
    public int getNpages() {
        return npages;
    }

    /**
     * @param npages the npages to set
     */
    public void setNpages(int npages) {
        this.npages = npages;
    }

    /**
     * @return the nres
     */
    public int getNres() {
        return nres;
    }

    /**
     * @param nres the nres to set
     */
    public void setNres(int nres) {
        this.nres = nres;
    }

    /**
     * @return the pageact
     */
    public int getPageact() {
        return pageact;
    }

    /**
     * @param pageact the pageact to set
     */
    public void setPageact(int pageact) {
        this.pageact = pageact;
    }

    /**
     * @return the nreg
     */
    public int getNreg() {
        return nreg;
    }

    /**
     * @param nreg the nreg to set
     */
    public void setNreg(int nreg) {
        this.nreg = nreg;
        this.npages=(int)Math.ceil((double)nreg/this.nres);
        this.pageact=1;
    }

    public int getActReg(){
        return (this.pageact-1)*this.nres;
    }

    public int getNextReg(){
        return this.pageact*this.nres+1;
    }

    public boolean isNext(){
        if (pageact==npages)
            return false;
        return true;
    }

    public boolean isPrev(){
        if (pageact==1)
            return false;
        return true;
    }

    public void reset(){
        this.npages=0;
        this.nreg=0;
        this.pageact=0;
    }

    public void next(){
        if (this.pageact<this.npages)
            this.pageact++;
    }

    public void prev(){
        if (this.pageact>1)
            this.pageact--;
    }
}
