package uk.ac.ebi.intact.export.mutation.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.intact.export.mutation.helper.FeatureToExportLine;
import uk.ac.ebi.intact.export.mutation.helper.model.ExportRange;
import uk.ac.ebi.intact.export.mutation.helper.model.MutationExportLine;
import uk.ac.ebi.intact.export.mutation.writer.FileExportHandler;
import uk.ac.ebi.intact.tools.feature.shortlabel.generator.events.UnmodifiedMutationShortlabelEvent;

import java.io.IOException;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class ExportMutationListener extends AbstractShortlabelGeneratorListener {
    private static final Log log = LogFactory.getLog(ExportMutationListener.class);

    private FileExportHandler fileExportHandler;

    public ExportMutationListener(FileExportHandler fileExportHandler) {
        this.fileExportHandler = fileExportHandler;
    }

    public void onUnmodifiedMutationShortlabel(UnmodifiedMutationShortlabelEvent event) {
        FeatureToExportLine featureToExportLine = new FeatureToExportLine();
        log.info("Convert " + event.getFeatureEvidence().getAc());
        MutationExportLine line = featureToExportLine.convertFeatureToMutationExportLine(event.getFeatureEvidence());
        log.info("Converted " + event.getFeatureEvidence().getAc());
        try {
            fileExportHandler.getExportWriter().writeHeaderIfNecessary("Feature AC", "Feature short label", "Feature range(s)", "Original sequence", "Resulting sequence", "Feature type", "Feature annotation", "Affected protein AC", "Affected protein symbol", "Affected protein full name", "Affected protein organism", "Interaction participants", "PubMedID", "Figure legend", "Interaction AC");
            fileExportHandler.getExportWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (line != null) {
                for (ExportRange exportRange : line.getExportRange()) {
                    fileExportHandler.getExportWriter().writeColumnValues(line.getFeatureAc(), line.getFeatureShortlabel(),
                            exportRange.getRange(), exportRange.getOriginalSequence(), exportRange.getResultingSequence(), line.getFeatureType(), line.getAnnotations(),
                            line.getAffectedProteinAc(), line.getAffectedProteinSymbol(), line.getAffectedProteinFullName(), line.getAffectedProteinOrganism(),
                            line.getParticipants(), line.getPubmedId(), line.getFigureLegend(), line.getInteractionAc());
                }
            }
            fileExportHandler.getExportWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
