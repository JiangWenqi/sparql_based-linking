package es.upm.oeg;

import es.upm.oeg.utils.StringUtil;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementFilter;
import org.apache.jena.sparql.syntax.ElementGroup;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Wenqi
 */
public class App {
    public static void main(String[] args) {

        String queryStr =
                "PREFIX :        <http://www.semanticweb.org/chetan/ontologies/2014/5/untitled-ontology-11#>\n" +
                        "PREFIX owl:     <http://www.w3.org/2002/07/owl#>\n" +
                        "PREFIX rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                        "PREFIX rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX foaf:    <http://xmlns.com/foaf/0.1/>\n" +
                        "CONSTRUCT {\n" +
                        "?city1 owl:sameAs ?city2 .\n" +
                        "} WHERE {\n" +
                        "    SERVICE <https://es.dbpedia.org/sparql> {\n" +
                        "        ?city1 foaf:name ?fullName1 .\n" +
                        "        VALUES ?city1 {\n" +
                        "            <http://es.dbpedia.org/resource/Sevilla>\n" +
                        "            <http://dbpedia.org/resource/Madrid>\n" +
                        "            <http://dbpedia.org/resource/Soria>\n" +
                        "        }\n" +
                        "    }\n" +
                        "    SERVICE <https://es.dbpedia.org/sparql> {\n" +
                        "        ?city2 foaf:name ?fullName2 .\n" +
                        "        VALUES ?city2 {\n" +
                        "            <http://dbpedia.org/resource/Madrid>\n" +
                        "            <http://dbpedia.org/resource/Soria>\n" +
                        "        }\n" +
                        "    }\n" +
                        "    FILTER ( ?fullName1 = ?fullName2 )\n" +
                        "}";


        Query query = QueryFactory.create(queryStr);
        ElementGroup queryPattern = (ElementGroup) query.getQueryPattern();
        for (Element element : queryPattern.getElements()) {
            if (element instanceof ElementFilter) {
                ElementFilter filter = (ElementFilter)element;
                System.out.println(filter.getExpr());
            }
        }

        // TODO jena filter similarity

        Model init = ModelFactory.createDefaultModel();
        QueryExecution queryExecution = QueryExecutionFactory.create(query, init);
        Model model = queryExecution.execConstruct();
//        model.write(System.out, "TURTLE");


    }
}
