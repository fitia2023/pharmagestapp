package mu.pharmagest.pharmagestapp.Controller.Vente;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import mu.pharmagest.pharmagestapp.util.SourceImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Desktop;
public class VentePDF {
    private static final String dest ="pdf/vente.pdf";
    public static void creatpdf(List<ModelVente> modelVentes, String medecin, String patient, String numticket, String dateticket) {


        try {
            PdfWriter pdfWriter = new PdfWriter(dest);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);

            Document document = new Document(pdfDocument, PageSize.A4);

// Ajout du titre et logo
            Paragraph titre = new Paragraph("Pharmagest").setBold().setFontSize(20);

            Image logo = new Image(ImageDataFactory.create(
                    SourceImage.getPathImage("logo.png"))
            );
            logo.scaleToFit(50, 50);

            Table e = new Table(2)
                    .addCell(new Cell().add(titre)
                            .setBorder(Border.NO_BORDER)
                            .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    )
                    .addCell(new Cell().add(logo)
                            .setBorder(Border.NO_BORDER)
                    ).addCell(new Cell(1, 2).add(
                                    new Paragraph(
                                            "Adesse: Port Louis 1234567 \n" +
                                                    "Tel: +230 464 0606"
                                    )
                            ).setBorder(Border.NO_BORDER)

                    );
            String avecPrescription = "";
            if (medecin != null) {
                avecPrescription =
                        "\n" +
                                "Médecin : " + medecin + "\n" +
                                "Nom de patient: " + patient;
            }

            document.add(
                    new Table(2)
                            .setWidth(UnitValue.createPercentValue(100))
                            .addCell(
                                    new Cell()
                                            .add(
                                                    e
                                            )
                                            .setWidth(UnitValue.createPercentValue(40))
                            )
                            .addCell(
                                    new Cell()
                                            .add(new Paragraph(
                                                    avecPrescription
                                            ).setPaddingLeft(100))
                                            .setBorder(Border.NO_BORDER)
                                            .setWidth(UnitValue.createPercentValue(60))
                            )

            );

            document.add(
                    new Table(2)
                            .setWidth(UnitValue.createPercentValue(100))
                            .setMarginTop(20)
                            .addCell(
                                    new Cell()
                                            .add(new Paragraph(
                                                    "Num ticket: " + numticket
                                            )).setBorder(Border.NO_BORDER)
                            )
                            .addCell(
                                    new Cell()
                                            .add(
                                                    new Paragraph(
                                                            "Date : " + dateticket
                                                    ).setTextAlignment(TextAlignment.RIGHT))
                                            .setBorder(Border.NO_BORDER)
                            )

            );

            // Création du tableau
            Table table = new Table(4);
            table.setWidth(UnitValue.createPercentValue(100));
            table.setMarginTop(20);
            table.addHeaderCell(
                    new Cell().add(new Paragraph("Libellés"))
                            .setBackgroundColor(new DeviceRgb(180, 180, 180))
                            .setBorder(Border.NO_BORDER)
                            .setWidth(UnitValue.createPercentValue(30)
                            )
            );
            table.addHeaderCell(
                    new Cell().add(new Paragraph("Prix Unitaire(RS)"))
                            .setBackgroundColor(new DeviceRgb(180, 180, 180))
                            .setBorder(Border.NO_BORDER)
                            .setWidth(UnitValue.createPercentValue(25))
            );
            table.addHeaderCell(
                    new Cell().add(new Paragraph("Qt"))
                            .setBackgroundColor(new DeviceRgb(180, 180, 180))
                            .setBorder(Border.NO_BORDER)
                            .setWidth(UnitValue.createPercentValue(20))
            );
            table.addHeaderCell(
                    new Cell().add(new Paragraph("Prix Totale(RS)"))
                            .setBackgroundColor(new DeviceRgb(180, 180, 180))
                            .setBorder(Border.NO_BORDER)
                            .setWidth(UnitValue.createPercentValue(25))
            );

            double total = 0.0;
            for (ModelVente modelVente : modelVentes) {
                table.addCell(new Cell().add(new Paragraph(modelVente.getMedicament().getNom_medicament())).setBorder(Border.NO_BORDER));
                table.addCell(new Cell().add(new Paragraph(modelVente.getPrice_vente().toString())).setBorder(Border.NO_BORDER));
                table.addCell(new Cell().add(new Paragraph(modelVente.getQt().toString())).setBorder(Border.NO_BORDER));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(modelVente.getPrice()))).setBorder(Border.NO_BORDER));
                total += modelVente.getPrice();
            }
            table.addCell(new Cell(1, 2).add(new Paragraph(" ")).setBorder(Border.NO_BORDER));
            Table totaletable = new Table(2);
            totaletable.setWidth(UnitValue.createPercentValue(100));
            totaletable.addCell(
                    new Cell()
                            .add(new Paragraph(
                                    "Prix:"
                            )).setBorder(Border.NO_BORDER)
                            .setWidth(UnitValue.createPercentValue(45))

            );
            totaletable.addCell(
                    new Cell()
                            .add(
                                    new Paragraph(
                                            total + " RS"
                                    ))
                            .setBorder(Border.NO_BORDER)
                            .setWidth(UnitValue.createPercentValue(55))
            );
            table.addCell(
                    new Cell(1, 2)
                            .add(totaletable)
            );
            // Ajout du tableau au document
            document.add(table);


            document.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }


    }

    public static void ouvrirpdf(){
        if (Desktop.isDesktopSupported() ){
            Desktop desktop = Desktop.getDesktop();
            File fichier = new File(dest);
            if (fichier.exists()){
                try {
                    desktop.open(fichier);
                } catch (IOException e) {
                    e.printStackTrace(); // Gérer l'erreur d'ouverture du fichier
                }
            }
        }
    }

}