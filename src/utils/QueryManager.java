package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Product;
import model.Member;
import model.Invoice;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import model.Account;

/**
 * The Query Manager is responsible for all the interaction with the database.
 * It uses the DataBaseManager @see(DbManager.class) to get to the database.
 */
public class QueryManager {

    private final DbManager dbmanager;

    public QueryManager(DbManager dbmanager) {
        this.dbmanager = dbmanager;
    }

    public Category getCategory(int categoryId) {
        Category category = null;
        try {
            String sql = "SELECT * FROM categorie WHERE categorieid='" + categoryId + "'";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                category = new Category(result.getInt("categorieid"),
                        result.getString("categorie_naam").trim(),
                        result.getString("categorie_omschrijving").trim());
            }
        } catch (SQLException e) {
            System.err.println(DbManager.SQL_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        }
        return category;
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<Category>();
        try {
            String sql = "SELECT * FROM categorie WHERE categorieid IN('1', '2', '3', '7') ORDER BY categorieid ASC";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                Category category = new Category(result.getInt("categorieid"),
                        result.getString("categorie_naam").trim(),
                        result.getString("categorie_omschrijving").trim());
                categories.add(category);
            }
        } catch (SQLException e) {
            System.err.println(DbManager.SQL_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        }
        return categories;
    }

    public List<Category> getProductCategories() {
        List<Category> categories = new ArrayList<Category>();
        try {
            String sql = "SELECT * FROM categorie ORDER BY categorieid ASC";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                Category category = new Category(result.getInt("categorieid"),
                        result.getString("categorie_naam").trim(),
                        result.getString("categorie_omschrijving").trim());
                categories.add(category);
            }
        } catch (SQLException e) {
            System.err.println(DbManager.SQL_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        }
        return categories;
    }

    public List<Category> getAbonnementen() {
        List<Category> abonnementen = new ArrayList<Category>();
        try {
            String sql = "SELECT productnummer, product_naam, product_prijs FROM product WHERE categorieid= 5 ORDER BY productnummer ASC ";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                Category abonnement = new Category(result.getInt("productnummer"),
                        result.getString("product_naam").trim(),
                        result.getString("product_prijs").trim());
                abonnementen.add(abonnement);
            }
        } catch (SQLException e) {
            System.err.println(DbManager.SQL_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        }
        return abonnementen;
    }

    public Product getProduct(int productId) {
        Product product = null;
        try {
            String sql = "SELECT * FROM product WHERE productnummer='" + productId + "'";
            ResultSet result = dbmanager.doQuery(sql);

            if (result.next()) {
                product = new Product(result.getInt("productnummer"),
                        result.getInt("categorieid"),
                        result.getString("product_naam"),
                        result.getString("product_omschrijving"),
                        result.getDouble("product_prijs"),
                        result.getInt("product_maxdeelnemers"),
                        result.getDate("product_startdatum"),
                        result.getDate("product_einddatum"),
                        result.getString("product_status"));
            }
        } catch (SQLException e) {
            System.err.println(DbManager.SQL_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        }
        return product;
    }

    public List<Product> getProducts(int categoryId) {
        List<Product> products = new ArrayList<Product>();
        try {
            String sql = "SELECT * FROM product "
                    + "WHERE categorieid='" + categoryId + "' AND product_status = 'Actief'"
                    + "ORDER BY product_naam ASC";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                products.add(new Product(result.getInt("productnummer"),
                        result.getInt("categorieid"),
                        result.getString("product_naam"),
                        result.getString("product_omschrijving"),
                        result.getDouble("product_prijs"),
                        result.getInt("product_maxdeelnemers"),
                        result.getDate("product_startdatum"),
                        result.getDate("product_einddatum"),
                        result.getString("product_status")));
            }
        } catch (SQLException e) {
            System.err.println(DbManager.SQL_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        }
        return products;
    }

    public void setOrder(model.Basket basket, int pasnummer, String opmerking, double total, int medewerkersnummer) {
        String sql = "INSERT INTO orders (ordernummer, lidnr, order_totaalbedrag, order_datum, medewerkersnr, order_status)"
                + " SELECT nextval('ordernummer'),lidnr, " + total + ", CURRENT_DATE, " + medewerkersnummer + ", 'open' FROM pas WHERE pasnummer = " + pasnummer
                + " RETURNING currval('ordernummer')";
        int ordernummer = 0;
        try {
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                ordernummer = result.getInt(1);
            }
        } catch (Exception e) {
            System.err.println(DbManager.SQL_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        }
        List<Product> products = basket.getProducts();
        for (Product product : products) {
            int productnummer = product.getProductId();
            int aantal = basket.getProductAmount(product);
            double prijs = product.getPrice();
            String SQL_orderProduct = "INSERT INTO orderregel (productnummer, ordernummer, orderregel_aantal, orderregel_prijs, orderregel_omschrijving)"
                    + " VALUES (" + productnummer + "," + ordernummer + "," + aantal + ", " + prijs + ", '')";
            dbmanager.executeQuery(SQL_orderProduct);
        }
    }

    public boolean insertLid(Member memberData, int memberID) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(main.PASApplication.DATE_FORMAT);
            Date date = new Date();
            java.sql.PreparedStatement ps = null;
            java.sql.PreparedStatement psAbonnement = null;
            //@TODO move to dbmanager
            if (memberID > 0) { //existing member
                ps = DbManager.getConn().prepareStatement("UPDATE lid SET lid_geslacht = ?,"
                        + "lid_voornaam = ?,"
                        + "lid_tussenvoegsel = ?,"
                        + "lid_achternaam = ?,"
                        + "lid_straat = ?,"
                        + "lid_huisnummer = ?,"
                        + "lid_huisnummertoevoeging = ?,"
                        + "lid_postcode = ?,"
                        + "lid_woonplaats = ?,"
                        + "lid_telefoon_dag = ?,"
                        + "lid_telefoon_nacht = ?,"
                        + "lid_gebdatum = ?,"
                        + "lid_reknr = ?,"
                        + "lid_emailadres = ?,"
                        + "vestigingscode = ? "
                        + "WHERE lidnr = ?; ");
            } else { //new member
                ps = DbManager.getConn().prepareStatement("INSERT INTO lid (lid_geslacht,lid_voornaam,lid_tussenvoegsel,"
                        + "lid_achternaam,lid_straat,lid_huisnummer,lid_huisnummertoevoeging,lid_postcode,"
                        + "lid_woonplaats, lid_telefoon_dag,lid_telefoon_nacht,lid_gebdatum,lid_reknr,lid_emailadres,"
                        + "vestigingscode, lid_status,lid_inschrijfdatum, medewerkersnr)"
                        + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            }
            ps.setString(1, memberData.getGender());
            ps.setString(2, memberData.getFirstname());
            ps.setString(3, memberData.getTussenVoeg());
            ps.setString(4, memberData.getLastname());
            ps.setString(5, memberData.getStraat());
            ps.setInt(6, memberData.getHuisnr());
            ps.setString(7, memberData.getHuisnrtoevoeg());
            ps.setString(8, memberData.getPostcode());
            ps.setString(9, memberData.getWoonplaats());
            ps.setString(10, memberData.getTelefoondag());
            ps.setString(11, memberData.getTelefoonnacht());
            ps.setDate(12, new java.sql.Date(memberData.getBirthday().getTime()));
            ps.setString(13, memberData.getReknr());
            ps.setString(14, memberData.getEmailAddress());
            ps.setString(15, memberData.getVestigingscode());
            if (memberID > 0) {
                ps.setInt(16, memberID);
                ps.executeUpdate();
                if (memberData.getAbonnement() > -1) {
                    psAbonnement = DbManager.getConn().prepareStatement("UPDATE lid_product_inschrijving SET productnummer = " + memberData.getAbonnement() + " WHERE lidnr = " + memberID + ";"
                            + "INSERT INTO lid_product_inschrijving (lidnr, productnummer) "
                            + "SELECT " + memberID + ", " + memberData.getAbonnement() + " WHERE NOT EXISTS(SELECT 1 FROM lid_product_inschrijving INNER JOIN product ON lid_product_inschrijving.productnummer = product.productnummer AND categorieid = 5 WHERE lidnr = " + memberID + ");");
                    psAbonnement.executeUpdate();
                }
                JOptionPane.showMessageDialog(null, "Lid met nummer " + memberID + " is succesvol bijgewerkt.");
            } else {
                ps.setString(16, "Actief");
                ps.setDate(17, new java.sql.Date(new java.util.Date().getTime()));
                ps.setInt(18, memberData.getMedewerkersnr());
                ps.executeUpdate();
                if (memberData.getAbonnement() > -1) {
                    psAbonnement = DbManager.getConn().prepareStatement("INSERT INTO lid_product_inschrijving (lidnr, productnummer) "
                            + "SELECT (SELECT MAX(lidnr) FROM lid), " + memberData.getAbonnement() + " WHERE NOT EXISTS(SELECT 1 FROM lid_product_inschrijving INNER JOIN product ON lid_product_inschrijving.productnummer = product.productnummer AND categorieid = 5 WHERE lidnr = " + memberID + ");");
                    psAbonnement.executeUpdate();
                }
                JOptionPane.showMessageDialog(null, "Lid is succesvol aangemaakt.");
            }

            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Er is een fout opgetreden bij het opslaan van het lid. Melding: \n" + ex.getMessage());
            return false;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Er is een fout opgetreden bij het opslaan van het lid. Melding:: \n" + ex.getMessage());
            return false;
        }
    }

    public boolean insertProduct(Product productData, int productID) {
        try {
            java.sql.PreparedStatement ps = null;

            if (productID > 0) { //existing product
                ps = DbManager.getConn().prepareStatement("UPDATE product SET product_naam = ?, "
                        + "product_omschrijving = ?, "
                        + "product_prijs = ?, "
                        + "product_maxdeelnemers =?, "
                        + "product_startdatum = ?, "
                        + "product_einddatum = ?, "
                        + "categorieid = ?, "
                        + "product_status = ? "
                        + "WHERE productnummer = ? ");
            } else { //new product
                ps = DbManager.getConn().prepareStatement("INSERT INTO product (product_naam, product_omschrijving, product_prijs, product_maxdeelnemers,"
                        + "product_startdatum, product_einddatum, categorieid, product_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            }
            ps.setString(1, productData.getName());
            ps.setString(2, productData.getDescription());
            ps.setDouble(3, productData.getPrice());
            ps.setInt(4, productData.getMaxDeelnemers());
            if (productData.getStartDatum() == null) {
                ps.setDate(5, null);
            } else {
                ps.setDate(5, new java.sql.Date(productData.getStartDatum().getTime()));
            }
            if (productData.getEindDatum() == null) {
                ps.setDate(6, null);
            } else {
                ps.setDate(6, new java.sql.Date(productData.getEindDatum().getTime()));
            }
            ps.setInt(7, productData.getCategorieId());
            ps.setString(8, productData.getStatus());
            if (productID > 0) {
                ps.setInt(9, productID);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, productData.getName() + " is succesvol bijgewerkt.");
            } else {
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Product is succesvol aangemaakt.");
            }
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Er is een fout opgetreden bij het opslaan van het product. Melding: \n" + ex.getMessage());
            return false;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Er is een fout opgetreden bij het opslaan van het product. Melding:: \n" + ex.getMessage());
            return false;
        }
    }

    public Member getMember(int memberId) {
        Member member = null;
        try {
            String sql = "SELECT lid.*, CASE WHEN lid_product_inschrijving.productnummer IS NULL THEN -1 ELSE lid_product_inschrijving.productnummer END AS \"abonnement\" FROM lid "
                    + "LEFT OUTER JOIN lid_product_inschrijving ON lid_product_inschrijving.lidnr = lid.lidnr "
                    + "LEFT OUTER JOIN product ON product.productnummer = lid_product_inschrijving.productnummer AND categorieid = 5 "
                    + "WHERE lid.lidnr='" + memberId + "'";
            ResultSet result = dbmanager.doQuery(sql);
            if (result.next()) {
                member = new Member(result.getInt("lidnr"),
                        result.getString("lid_voornaam"),
                        result.getString("lid_achternaam"),
                        result.getString("lid_geslacht"),
                        result.getDate("lid_gebdatum"),
                        result.getString("lid_tussenvoegsel"),
                        result.getString("lid_straat"),
                        result.getInt("lid_huisnummer"),
                        result.getString("lid_huisnummertoevoeging"),
                        result.getString("lid_postcode"),
                        result.getString("lid_woonplaats"),
                        result.getString("lid_telefoon_dag"),
                        result.getString("lid_telefoon_nacht"),
                        result.getString("lid_reknr"),
                        result.getString("lid_status"),
                        result.getString("lid_foto"),
                        result.getDate("lid_inschrijfdatum"),
                        result.getDate("lid_uitschrijfdatum"),
                        result.getInt("medewerkersnr"),
                        result.getString("vestigingscode"),
                        result.getString("lid_emailadres"),
                        result.getInt("abonnement"));
            }
        } catch (SQLException e) {
            System.err.println(DbManager.SQL_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        }
        return member;
    }

    public Member getMemberPas(int pasNummer) {
        Member member = null;
        try {
            String sql = "SELECT lid.*, CASE WHEN lid_product_inschrijving.productnummer IS NULL THEN -1 ELSE lid_product_inschrijving.productnummer END AS \"abonnement\" "
                    + "FROM lid INNER JOIN pas ON lid.lidnr = pas.lidnr "
                    + "LEFT OUTER JOIN lid_product_inschrijving ON lid_product_inschrijving.lidnr = lid.lidnr "
                    + "LEFT OUTER JOIN product ON product.productnummer = lid_product_inschrijving.productnummer AND categorieid = 5"
                    + "WHERE pasnummer='" + pasNummer + "'";
            ResultSet result = dbmanager.doQuery(sql);
            if (result.next()) {
                member = new Member(result.getInt("lidnr"),
                        result.getString("lid_voornaam"),
                        result.getString("lid_achternaam"),
                        result.getString("lid_geslacht"),
                        result.getDate("lid_gebdatum"),
                        result.getString("lid_tussenvoegsel"),
                        result.getString("lid_straat"),
                        result.getInt("lid_huisnummer"),
                        result.getString("lid_huisnummertoevoeging"),
                        result.getString("lid_postcode"),
                        result.getString("lid_woonplaats"),
                        result.getString("lid_telefoon_dag"),
                        result.getString("lid_telefoon_nacht"),
                        result.getString("lid_reknr"),
                        result.getString("lid_status"),
                        result.getString("lid_foto"),
                        result.getDate("lid_inschrijfdatum"),
                        result.getDate("lid_uitschrijfdatum"),
                        result.getInt("medewerkersnr"),
                        result.getString("vestigingscode"),
                        result.getString("lid_emailadres"),
                        result.getInt("abonnemnt"));
            }
        } catch (SQLException e) {
            System.err.println(DbManager.SQL_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        }
        return member;
    }

    public String[][] getMemberList() throws SQLException {
        String sql = "SELECT lid.lidnr, lid_voornaam || CASE WHEN lid_tussenvoegsel > '' THEN ' ' || lid_tussenvoegsel ELSE ''  END || ' '|| lid_achternaam AS \"lid_naam\", lid_inschrijfdatum, CASE WHEN lid_geslacht = 'M' THEN 'De heer' ELSE 'Mevrouw' END AS \"lid_geslacht\", lid_woonplaats, lid_status, CASE WHEN bezoek_sportschool.pasnummer IS NOT NULL THEN 'Ja' ELSE 'Nee' END AS \"ingecheckt\""
                + " FROM lid "
                + " LEFT OUTER JOIN pas ON lid.lidnr = pas.lidnr"
                + " LEFT OUTER JOIN bezoek_sportschool ON pas.pasnummer = bezoek_sportschool.pasnummer AND bezoek_einde IS NULL"
                + " ORDER BY lid.lidnr DESC";
        ResultSet result = dbmanager.doQuery(sql);
        int rowcount = 0;
        if (result.last()) {
            rowcount = result.getRow();
            result.beforeFirst();
        }
        String[][] returnList = new String[rowcount][7];
        int i = 0;
        while (result.next()) {
            returnList[i][0] = result.getString("lidnr").trim();
            returnList[i][1] = result.getString("lid_geslacht").trim();
            returnList[i][2] = result.getString("lid_naam").trim();
            returnList[i][3] = result.getString("lid_woonplaats").trim();
            returnList[i][4] = result.getString("lid_inschrijfdatum").trim();
            returnList[i][5] = result.getString("lid_status").trim();
            returnList[i][6] = result.getString("ingecheckt").trim();
            i++;
        }
        return returnList;
    }

    public String[][] getProductList() throws SQLException {
        String sql = "SELECT * "
                + "FROM product "
                + "INNER JOIN categorie ON product.categorieid = categorie.categorieid "
                + "ORDER BY productnummer DESC";
        ResultSet result = dbmanager.doQuery(sql);
        int rowcount = 0;
        if (result.last()) {
            rowcount = result.getRow();
            result.beforeFirst();
        }
        String[][] returnList = new String[rowcount][9];
        int i = 0;
        while (result.next()) {
            returnList[i][0] = result.getString("productnummer");
            returnList[i][1] = result.getString("product_naam");
            returnList[i][2] = result.getString("product_omschrijving");
            returnList[i][3] = result.getString("product_prijs");
            returnList[i][4] = result.getString("product_maxdeelnemers");
            returnList[i][5] = result.getString("product_startdatum");
            returnList[i][6] = result.getString("product_einddatum");
            returnList[i][7] = result.getString("categorie_naam");
            returnList[i][8] = result.getString("product_status");
            i++;
        }
        return returnList;
    }

    public String pasCheckInUit(String pasNummer, String vestigingsCode) throws SQLException {

        String checkInUitMelding;
        checkInUitMelding = "";

        String selectPasEnLid = "SELECT pasnummer, lid_voornaam, lid_achternaam, lid_foto FROM pas INNER JOIN lid ON pas.lidnr = lid.lidnr WHERE pas.pasnummer = " + "'" + pasNummer + "' AND pas.pas_status = 'actief'";
        ResultSet result = dbmanager.doQuery(selectPasEnLid);

        int rowcountPas = 0;
        if (result.last()) {
            rowcountPas = result.getRow();
            result.first();
        }

        // pas bestaat niet of is niet actief
        if (rowcountPas == 0) {
            checkInUitMelding = "Pasnummer: " + pasNummer + " bestaat niet of is niet actief!";
        } // pas bestaat wel
        else if (rowcountPas == 1) {
            String selectCheckInUit = "SELECT bezoek_start, bezoek_einde, vestigingscode FROM bezoek_sportschool WHERE pasnummer = " + "'" + pasNummer + "' AND vestigingscode = " + "'" + vestigingsCode + "'" + "AND bezoek_einde IS NULL";
            ResultSet resultCheckInUit = dbmanager.doQuery(selectCheckInUit);

            int rowcountCheckInUit = 0;
            if (resultCheckInUit.last()) {
                rowcountCheckInUit = resultCheckInUit.getRow();
                resultCheckInUit.first();
            }

            // geen bezoek gevonden op dit pasnummer in combinatie met de vestigingscode, dus nieuw bezoek toevoegen
            if (rowcountCheckInUit == 0) {
                String insertCheckIn = "INSERT INTO bezoek_sportschool (pasnummer, bezoek_start, bezoek_einde, vestigingscode) "
                        + "VALUES ('" + pasNummer + "', now(), NULL, '" + vestigingsCode + "')";
                dbmanager.executeQuery(insertCheckIn);
                checkInUitMelding = "Welkom " + result.getString("lid_voornaam") + " " + result.getString("lid_achternaam");

            } // wel een bezoek gevonden, dus eindtijd vullen
            else if (rowcountPas == 1) {
                String insertCheckUit = "UPDATE bezoek_sportschool SET bezoek_einde = now() WHERE pasnummer = " + "'" + pasNummer + "' AND bezoek_einde IS NULL";
                dbmanager.executeQuery(insertCheckUit);

                checkInUitMelding = "Tot ziens " + result.getString("lid_voornaam") + " " + result.getString("lid_achternaam");
            }
        }
        return checkInUitMelding;
    }

    public void insertReportParameter(String reportType, String name, String value) {
        String stmt = "INSERT INTO reportselection (reportname, parametername, parametervalue) VALUES ('" + reportType + "','" + name + "','" + value + "')";
        dbmanager.executeQuery(stmt);
    }

    public void deleteReportParameter(String reportType) {
        String stmt = "DELETE FROM reportselection WHERE reportname = '" + reportType + "'";
        dbmanager.executeQuery(stmt);
    }

    public String[][] getAllInvoice() {
        List<Invoice> facturen = new ArrayList<Invoice>();
        try {
            String sql = "SELECT *, (SELECT lid_voornaam || ' ' || lid_tussenvoegsel || ' ' || lid_achternaam FROM lid WHERE lidnr = f.lidnr) as lidnaam, factuur_status FROM facturen f";
            ResultSet result = dbmanager.doQuery(sql);

            int rowcount = 0;
            if (result.last()) {
                rowcount = result.getRow();
                result.beforeFirst();
            }
            String[][] returnList = new String[rowcount][7];
            int i = 0;
            while (result.next()) {
                returnList[i][0] = result.getString("factuurnummer");
                returnList[i][1] = result.getString("factuur_datum");
                returnList[i][2] = result.getString("factuur_vervaldatum");
                returnList[i][3] = result.getString("lidnr");
                returnList[i][4] = result.getString("factuur_totaalbedrag");
                returnList[i][5] = result.getString("lidnaam");
                returnList[i][6] = result.getString("factuur_status");
                i++;
            }
            return returnList;
        } catch (SQLException e) {
            System.err.println(DbManager.SQL_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        }
        return new String[1][1];
    }

    public Invoice getInvoice(int iInvoiceNr) throws SQLException {
        String sql = "SELECT *, (SELECT lid_voornaam || ' ' || lid_tussenvoegsel || ' ' || lid_achternaam FROM lid WHERE lidnr = f.lidnr) as lidnaam FROM facturen f WHERE factuurnummer = " + iInvoiceNr;
        ResultSet result = dbmanager.doQuery(sql);

        model.Invoice returnInvoice = null;
        while (result.next()) {
            returnInvoice = new model.Invoice(result.getInt("factuurnummer"),
                    result.getDate("factuur_datum"),
                    result.getDate("factuur_vervaldatum"),
                    result.getInt("lidnr"),
                    result.getDouble("factuur_totaalbedrag"),
                    result.getString("lidnaam"),
                    result.getString("factuur_status"));
        }


        return returnInvoice;
    }

    public List<model.InvoiceRule> getInvoiceRules(int iInvoiceNr) throws SQLException {
        String sql = "SELECT * FROM factuurregel f WHERE factuurnummer = " + iInvoiceNr;
        ResultSet result = dbmanager.doQuery(sql);

        List<model.InvoiceRule> returnInvoiceRule = new ArrayList<model.InvoiceRule>();
        while (result.next()) {
            returnInvoiceRule.add(new model.InvoiceRule(result.getInt("productnummer"),
                    result.getInt("factuurregel_aantal"),
                    result.getDouble("factuurregel_prijs"),
                    result.getString("factuurregel_omschrijving").trim()));
            break;
        }


        return returnInvoiceRule;
    }

    public boolean setFacturen(Object[] excluded) {
        String strExcluded = "";
        for (int i = 0; i < excluded.length; i++) {

            if (i == (excluded.length - 1)) {
                strExcluded += excluded[i];
            } else {
                strExcluded += excluded[i] + ",";
            }

        }

        String sql = "INSERT INTO facturen (factuurnummer, lidnr, factuur_totaalbedrag, factuur_datum, factuur_vervaldatum, factuur_status) "
                + "SELECT (lidnr::character varying || DATE_PART('YEAR', now()) || DATE_PART('MONTH', now()))::integer as factnr, lidnr, SUM(order_totaalbedrag) as totaal, now() as factuurdat, now() + '1 month' as verval, 'open' as factuurstatus FROM orders ord "
                + "WHERE order_datum <= (date_trunc('month', now()) + '26 days') AND order_datum >= ((date_trunc('month', now()) + '26 days') + '-1 month') "
                + "AND NOT EXISTS(SELECT * FROM facturen where factuurnummer = (ord.lidnr::character varying || DATE_PART('YEAR', now()) || DATE_PART('MONTH', now()))::integer) "
                + (strExcluded.length() > 0 ? " AND (lidnr::character varying || DATE_PART('YEAR', now()) || DATE_PART('MONTH', now()))::integer NOT IN (" + strExcluded + ") " : "")
                + "GROUP BY lidnr";
        System.out.println(sql);
        if (dbmanager.executeQuery(sql)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean setFactuurregels(Object[] excluded) {
        String strExcluded = "";
        for (int i = 0; i < excluded.length; i++) {
            if (i == (excluded.length - 1)) {
                strExcluded += excluded[i];
            } else {
                strExcluded += excluded[i] + ",";
            }
        }

        String sql = "INSERT INTO factuurregel "
                + "(factuurnummer, productnummer, factuurregel_aantal, factuurregel_prijs, factuurregel_omschrijving)"
                + " SELECT (o.lidnr::character varying || DATE_PART('YEAR', now()) || DATE_PART('MONTH', now()))::integer as factnum, ro.productnummer, sum(orderregel_aantal), sum(orderregel_prijs), '' FROM orderregel ro "
                + " INNER JOIN orders o "
                + " ON ro.ordernummer = o.ordernummer "
                + " INNER JOIN product p "
                + " ON ro.productnummer = p.productnummer "
                + " WHERE ((o.order_datum <= (date_trunc('month', now()) + '26 days') AND o.order_datum >= ((date_trunc('month', now()) + '26 days') + '-1 month')) "
                + " OR  (p.categorieid = 4 AND p.product_startdatum <= (date_trunc('month', now()) + '-2 month 26 days')) AND p.product_einddatum >= (date_trunc('month', now()) + '1 month 26 days')) "
                + (strExcluded.length() > 0 ? " AND (o.lidnr::character varying || DATE_PART('YEAR', now()) || DATE_PART('MONTH', now()))::integer NOT IN (" + strExcluded + ") " : "")
                + " AND NOT EXISTS(SELECT * FROM factuurregel where factuurnummer = (o.lidnr::character varying || DATE_PART('YEAR', now()) || DATE_PART('MONTH', now()))::integer) "
                + " GROUP BY factnum, ro.productnummer";
        System.out.println(sql);
        if (dbmanager.executeQuery(sql)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean insertAccount(Account medewerkerData, int accountID) {
        try {
            java.sql.PreparedStatement ps = null;

            if (accountID > 0) { //existing account
                ps = DbManager.getConn().prepareStatement("UPDATE medewerker SET medew_geslacht = ?, "
                        + "medew_voornaam = ?, "
                        + "medew_tussenvoegsel = ?, "
                        + "medew_achternaam =?, "
                        + "medew_wachtwoord = ?, "
                        + "vestigingscode = ?, "
                        + "rolcode = ?, "
                        + "medew_status = ? "
                        + "WHERE medewerkersnr = ? ");
            } else { //new account
                ps = DbManager.getConn().prepareStatement("INSERT INTO medewerker (medew_geslacht, medew_voornaam, medew_tussenvoegsel, medew_achternaam,"
                        + "medew_wachtwoord, vestigingscode, rolcode, medew_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            }
            ps.setString(1, medewerkerData.getMedew_geslacht());
            ps.setString(2, medewerkerData.getmedew_voornaam());
            ps.setString(3, medewerkerData.getmedew_tussenvoegsel());
            ps.setString(4, medewerkerData.getmedew_achternaam());
            ps.setString(5, medewerkerData.getMedew_wachtwoord());
            ps.setString(6, medewerkerData.getVestigingscode());
            ps.setInt(7, medewerkerData.getRolcode());
            ps.setString(8, medewerkerData.getMedew_status());

            if (accountID > 0) {
                ps.setInt(9, accountID);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Medewerker met medewerkersnr " + accountID + " is succesvol bijgewerkt.");
            } else {
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Medewerker is succesvol aangemaakt.");
            }
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Er is een fout opgetreden bij het opslaan van de medewerker. Melding: \n" + ex.getMessage());
            return false;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Er is een fout opgetreden bij het opslaan van de medewerker. Melding:: \n" + ex.getMessage());
            return false;
        }
    }

    public void setInvoicePaid(int iInvoiceNr) {
        String sql = "UPDATE facturen SET factuur_status = 'betaald' WHERE factuurnummer = " + iInvoiceNr;
        dbmanager.executeQuery(sql);
    }

    public void setInvoiceNotPaid(int iInvoiceNr) {
        String sql = "UPDATE facturen SET factuur_status = 'storno' WHERE factuurnummer = " + iInvoiceNr;
        dbmanager.executeQuery(sql);
    }

    /**
     * @author Thom/Oscar
     * @param username haalt de username op die word bepaald als medewerkersnummer 
     * @param password haalt de password op uit de database 
     * @return  voert de query uit en controlleerd de gegevens 
     */
    public boolean checkMedewerker(String username, String password) {
        String stmt2 = "SELECT medewerkersnr FROM medewerker WHERE medewerkersnr = '" + username + "' AND medew_wachtwoord = '" + password + "'";
        try {
            ResultSet result = dbmanager.doQuery(stmt2);
            while (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String[][] getAccountList() throws SQLException {
        String sql = "SELECT * "
                + "FROM medewerker "
                + "ORDER BY medewerkersnr DESC";
        ResultSet result = dbmanager.doQuery(sql);
        int rowcount = 0;
        if (result.last()) {
            rowcount = result.getRow();
            result.beforeFirst();
        }
        String[][] returnList = new String[rowcount][9];
        int i = 0;
        while (result.next()) {
            returnList[i][0] = result.getString("medewerkersnr");
            returnList[i][1] = result.getString("medew_geslacht");
            returnList[i][2] = result.getString("medew_voornaam");
            returnList[i][3] = result.getString("medew_tussenvoegsel");
            returnList[i][4] = result.getString("medew_achternaam");
            returnList[i][5] = result.getString("medew_wachtwoord");
            returnList[i][6] = result.getString("vestigingscode");
            returnList[i][7] = result.getString("rolcode");
            returnList[i][8] = result.getString("medew_status");
            i++;
        }
        return returnList;
    }

    public Account getAccount(int medewerkers_nr) {
        Account account = null;
        try {
            String sql = "SELECT * FROM medewerker WHERE medewerkersnr='" + medewerkers_nr + "'";
            ResultSet result = dbmanager.doQuery(sql);
            if (result.next()) {
                account = new Account(result.getInt("medewerkersnr"),
                        result.getString("medew_voornaam"),
                        result.getString("medew_tussenvoegsel"),
                        result.getString("medew_achternaam"),
                        result.getString("medew_wachtwoord"),
                        result.getString("medew_geslacht"),
                        result.getString("vestigingscode"),
                        result.getInt("rolcode"),
                        result.getString("medew_status"));
            }
        } catch (SQLException e) {
            System.err.println(DbManager.SQL_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        }
        return account;
    }

    /*public void updateProduct(String[] productData, int productID) {
    String stmtP = "UPDATE product SET product_naam = " + productData[0] + ", "
    + "product_omschrijving = " + productData[1] + ", "
    + "product_prijs = " + productData[2] + ", "
    + "product_maxdeelnemers = " + productData[3] + ", "
    + "product_startdatum = " + productData[4] + ", "
    + "product_einddatum = " + productData[5] + ", "
    + "categorieid = " + productData[6] + ", "
    + "product_status = " + productData[7]
    + "WHERE productnummer = " + productID;
    
    dbmanager.executeQuery(stmtP);
    
    }
    public void updateAccount(String[] accountData, int accountID) {
    String stmtA = "UPDATE medewerker SET medew_geslacht = " + accountData[0] + ", "
    + "medew_voornaam = " + accountData[1] + ", "
    + "medew_tussenvoegsel = " + accountData[2] + ", "
    + "medew_achternaam = " + accountData[3] + ", "
    + "medew_wachtwoord = " + accountData[4] + ", "
    + "vestigingscode = " + accountData[5] + ", "
    + "rolcode = " + accountData[6] + ", "
    + "medew_status = " + accountData[7]
    + " WHERE medewerkersnr = " + accountID;
    dbmanager.executeQuery(stmtA);
    
    }*/
    public String[][] getCursusList() throws SQLException {
        String sql = "SELECT productnummer, product_naam, product_maxdeelnemers, product_startdatum, product_einddatum, (SELECT COUNT(*) FROM lid_product_inschrijving WHERE product.productnummer = lid_product_inschrijving.productnummer) AS \"Ingeschreven\" FROM product "
                + "WHERE categorieid = 4 AND product_startdatum <= now() AND product_einddatum >= now() AND product_status='Actief'";
        ResultSet result = dbmanager.doQuery(sql);
        int rowcount = 0;
        if (result.last()) {
            rowcount = result.getRow();
            result.beforeFirst();
        }
        String[][] returnList = new String[rowcount][6];
        int i = 0;
        while (result.next()) {
            returnList[i][0] = result.getString("productnummer");
            returnList[i][1] = result.getString("product_naam");
            returnList[i][2] = result.getString("product_maxdeelnemers");
            returnList[i][3] = result.getString("product_startdatum");
            returnList[i][4] = result.getString("product_einddatum");
            returnList[i][5] = result.getString("ingeschreven");
            i++;
        }
        return returnList;
    }

    public String cursusInschrijving(String pasNummer, String productNummer) throws SQLException {

        String inschrijvingMelding;
        inschrijvingMelding = "";

        String selectMaxDeelnemers = "SELECT product_maxdeelnemers FROM product WHERE productnummer = " + productNummer + " AND product_maxdeelnemers = (SELECT COUNT(*) FROM lid_product_inschrijving WHERE productnummer = " + productNummer + ")";
        ResultSet resultMaxDeelnemers = dbmanager.doQuery(selectMaxDeelnemers);

        int rowcountVol = 0;
        if (resultMaxDeelnemers.last()) {
            rowcountVol = resultMaxDeelnemers.getRow();
            resultMaxDeelnemers.first();
        }

        if (rowcountVol == 0) {

            String selectPasEnLid = "SELECT pasnummer, lid_voornaam || CASE WHEN lid_tussenvoegsel > '' THEN ' ' || lid_tussenvoegsel ELSE ''  END || ' '|| lid_achternaam AS \"lid_naam\", pas.lidnr FROM pas INNER JOIN lid ON pas.lidnr = lid.lidnr WHERE pas.pasnummer = " + "'" + pasNummer + "' AND pas.pas_status = 'actief'";
            ResultSet result = dbmanager.doQuery(selectPasEnLid);

            int rowcountPas = 0;
            if (result.last()) {
                rowcountPas = result.getRow();
                result.first();
            }
            // pas bestaat niet of is niet actief
            if (rowcountPas == 0) {
                inschrijvingMelding = "Pasnummer: " + pasNummer + " bestaat niet of is niet actief!";
            } // pas bestaat wel
            else if (rowcountPas == 1) {

                String selectLidIngeschreven = "SELECT lid_product_inschrijving.lidnr "
                        + "FROM lid_product_inschrijving "
                        + "INNER JOIN pas ON lid_product_inschrijving.lidnr = pas.lidnr "
                        + "WHERE pasnummer = " + pasNummer + " AND productnummer = " + productNummer;
                ResultSet resultIngeschreven = dbmanager.doQuery(selectLidIngeschreven);

                int rowcountIngeschreven = 0;
                if (resultIngeschreven.last()) {
                    rowcountIngeschreven = resultIngeschreven.getRow();
                    resultIngeschreven.first();
                }

                if (rowcountIngeschreven == 0) {

                    String insertCheckIn = "INSERT INTO lid_product_inschrijving (lidnr, productnummer) "
                            + "SELECT lidnr, " + productNummer + "FROM pas WHERE pasnummer = " + pasNummer;

                    dbmanager.executeQuery(insertCheckIn);
                    inschrijvingMelding = result.getString("lid_naam") + " is succesvol ingeschreven!";
                } else if (rowcountIngeschreven == 1) {
                    inschrijvingMelding = "Lid staat al ingeschreven voor deze cursus!";
                }
            }
        } else if (rowcountVol == 1) {
            inschrijvingMelding = "Inschrijving niet mogelijk, de cursus heeft het maximaal aantal deelnemers bereikt!";
        }
        return inschrijvingMelding;
    }

    public void insertPhoto(String foto, String lidId) {
        String stmt = "UPDATE lid SET lid_foto = '" + foto + "' WHERE lidnr = " + lidId;
        dbmanager.executeQuery(stmt);
    }

    public List<Invoice> selectInvoices() {
        List<Invoice> inv = new ArrayList<Invoice>();
        try {
            String sql = "SELECT (lidnr::character varying || DATE_PART('YEAR', now()) || DATE_PART('MONTH', now()))::integer as factnr, lidnr, SUM(order_totaalbedrag) as totaal, now() as factuurdat, now() + '1 month' as verval FROM orders ord "
                    + "WHERE order_datum <= (date_trunc('month', now()) + '26 days') AND order_datum >= ((date_trunc('month', now()) + '26 days') + '-1 month') "
                    + "AND NOT EXISTS(SELECT * FROM facturen where factuurnummer = (ord.lidnr::character varying || DATE_PART('YEAR', now()) || DATE_PART('MONTH', now()))::integer) "
                    + "GROUP BY lidnr";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                inv.add(new Invoice(result.getInt("factnr"),
                        new Date(result.getDate("factuurdat").getTime()),
                        new Date(result.getDate("verval").getTime()),
                        result.getInt("lidnr"),
                        result.getDouble("totaal")));
            }
        } catch (SQLException e) {
            System.err.println(DbManager.SQL_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        }
        return inv;
    }

    public int getRol() {
        try {
            String sql = "SELECT rolcode FROM medewerker WHERE medewerkersnr = " + view.Login.employeecode;
            ResultSet result = dbmanager.doQuery(sql);
            int iRolId = 0;
            while (result.next()) {
                iRolId = result.getInt("rolcode");
            }
            return iRolId;
        } catch (SQLException e) {
            System.out.println("Rol kon niet worden opgehaald.");
            e.printStackTrace(System.err);
        }
        return 0;
    }
}
