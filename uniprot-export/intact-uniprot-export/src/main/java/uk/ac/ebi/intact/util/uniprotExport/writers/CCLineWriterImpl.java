package uk.ac.ebi.intact.util.uniprotExport.writers;

import uk.ac.ebi.intact.util.uniprotExport.event.CcLineCreatedEvent;
import uk.ac.ebi.intact.util.uniprotExport.event.CcLineEventListener;
import uk.ac.ebi.intact.util.uniprotExport.parameters.CCParameters;
import uk.ac.ebi.intact.util.uniprotExport.parameters.InteractionDetails;
import uk.ac.ebi.intact.util.uniprotExport.parameters.SecondCCInteractor;

import javax.swing.event.EventListenerList;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * Default writer for CCLines
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/01/11</pre>
 */

public class CCLineWriterImpl implements CCLineWriter{

    /**
     * The writer
     */
    private OutputStreamWriter writer;
    protected EventListenerList listenerList = new EventListenerList();

    /**
     * Create a new CCLine writer with a fileName
     * @param outputStream : the outputStreamWriter
     * @throws IOException
     */
    public CCLineWriterImpl(OutputStreamWriter outputStream) throws IOException {
        if (outputStream == null){
            throw new IllegalArgumentException("You must give a non null OutputStream writer");
        }
        writer = outputStream;
    }

    @Override
    public void writeCCLine(CCParameters parameters) throws IOException {

        // if parameter not null, write it
        if (parameters != null){

            // write the title
            writeCCLineTitle(parameters.getFirstInteractor());

            // write the content
            writeCCLineParameters(parameters);

            writer.flush();
        }
    }

    @Override
    public void writeCCLines(List<CCParameters> CCLines) throws IOException {

        // write each CCParameter
        for (CCParameters parameter : CCLines){
            writeCCLine(parameter);
        }
    }

    /**
     * Write the content of the CC line
     * @param parameters : the parameters
     */
    public void writeCCLineParameters(CCParameters parameters) throws IOException {

        String firstUniprotAc = parameters.getFirstInteractor();
        String firstIntactAc = parameters.getFirstIntacAc();
        String firstTaxId = parameters.getFirstTaxId();

        for (SecondCCInteractor secondInteractor : parameters.getSecondCCInteractors()){
            // write introduction
            writeInteractionIntroduction(true, firstUniprotAc, firstIntactAc, secondInteractor.getSecondInteractor(), secondInteractor.getSecondIntactAc());

            // write first protein
            writeFirstProtein(firstUniprotAc, firstIntactAc);

            // write second protein
            writeSecondProtein(secondInteractor.getSecondInteractor(), secondInteractor.getSecondGeneName(),
                    firstTaxId, secondInteractor.getSecondTaxId(), secondInteractor.getSecondOrganismName());

            // write the details of the interaction
            writeInteractionDetails(secondInteractor.getInteractionDetails());
        }
        writer.write("//");
        writer.write(WriterUtils.NEW_LINE);
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }

    /**
     * Write the CC line title
     * @param uniprot1
     */
    private void writeCCLineTitle(String uniprot1) throws IOException {
        writer.write("AC   ");
        writer.write(uniprot1);
        writer.write(WriterUtils.NEW_LINE);
        writer.write("CC   -!- INTERACTION:");
        writer.write(WriterUtils.NEW_LINE);
    }

    void fireCcLineCreatedEvent(CcLineCreatedEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == CcLineEventListener.class) {
                ((CcLineEventListener) listeners[i + 1]).ccLineCreated(evt);
            }
        }
    }

    public void addCcLineExportListener(CcLineEventListener eventListener) {
        listenerList.add(CcLineEventListener.class, eventListener);
    }

    // This methods allows classes to unregister for MyEvents
    public void removeCcLineExportListener(CcLineEventListener eventListener) {
        listenerList.remove(CcLineEventListener.class, eventListener);
    }

    /**
     * Write the introduction of a CC line
     * @param doesInteract
     * @param uniprot1
     * @param intact1
     * @param uniprot2
     * @param intact2
     */
    private void writeInteractionIntroduction(boolean doesInteract, String uniprot1, String intact1, String uniprot2, String intact2) throws IOException {
        writer.write("CC       Interact=");
        writer.write((doesInteract ? "yes" : "no"));
        writer.write("; ");

        writer.write(" Xref=IntAct:");
        writer.write( intact1 );
        writer.write(',');
        writer.write(intact2);
        writer.write(';');
        writer.write(WriterUtils.NEW_LINE);
    }

    /**
     * Write the first protein of a CCLine
     * @param uniprot1
     * @param geneName1
     */
    private void writeFirstProtein(String uniprot1, String geneName1) throws IOException {
        writer.write("CC         Protein1=");
        writer.write(geneName1);
        writer.write(" [");
        writer.write( uniprot1 );
        writer.write( "];" );
        writer.write(WriterUtils.NEW_LINE);
    }

    /**
     * Write the second protein of a CCLine
     * @param uniprot2
     * @param geneName2
     * @param taxId1
     * @param taxId2
     * @param organism2
     */
    private void writeSecondProtein(String uniprot2, String geneName2, String taxId1, String taxId2, String organism2) throws IOException {
        writer.write("CC         Protein2=");
        writer.write(geneName2);
        writer.write(" [");
        writer.write( uniprot2 );
        writer.write("];");

        if (!taxId1.equalsIgnoreCase(taxId2)) {
            writer.write(" Organism=");
            writer.write(organism2);
            writer.write( " [NCBI_TaxID=" );
            writer.write( taxId2 );
            writer.write( "];" );
        }

        writer.write(WriterUtils.NEW_LINE);
    }

    /**
     * Write the details of a binary interaction
     * @param interactionDetails
     */
    private void writeInteractionDetails(SortedSet<InteractionDetails> interactionDetails) throws IOException {

        // collect all pubmeds and spoke expanded information
        for (InteractionDetails details : interactionDetails){
            String type = details.getInteractionType();
            String method = details.getDetectionMethod();

            if (details.isSpokeExpanded()){
                writeSpokeExpandedInteractions(type, method, details.getPubmedIds());
            }
            else{
                writeBinaryInteraction(type, method, details.getPubmedIds());
            }
        }
    }

    /**
     * Write the details of a spoke expanded interaction
     * @param type
     * @param method
     * @param spokeExpandedPubmeds
     */
    private void writeSpokeExpandedInteractions(String type, String method, Set<String> spokeExpandedPubmeds) throws IOException {
        writer.write("CC         InteractionType=");
        writer.write(type);
        writer.write("; Method=");
        writer.write(method);
        writer.write("; Expansion=Spoke; Source=");

        int index = 0;
        int size = spokeExpandedPubmeds.size();
        for (String pid : spokeExpandedPubmeds){
            index++;
            writer.write("Pubmed:");
            writer.write(pid);

            if (index == size){
                writer.write(";");
            }
            else{
                writer.write(", ");
            }
        }
        writer.write(WriterUtils.NEW_LINE);
    }

    /**
     * write the details of a true binary interaction
     * @param type
     * @param method
     * @param binaryInteractions
     */
    private void writeBinaryInteraction(String type, String method, Set<String> binaryInteractions) throws IOException {
        writer.write("CC         InteractionType=");
        writer.write(type);
        writer.write("; Method=");
        writer.write(method);
        writer.write("; Source=");

        int index = 0;
        int size = binaryInteractions.size();
        for (String pid : binaryInteractions){
            index++;
            writer.write("Pubmed:");
            writer.write(pid);

            if (index == size){
                writer.write(";");
            }
            else{
                writer.write(", ");
            }
        }
        writer.write(WriterUtils.NEW_LINE);
    }
}
