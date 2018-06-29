package eu.kidf.divapi;

import java.util.Map;
import java.util.Set;


/**
 * Semantic Matching API
 * This interface defines access methods to background knowledge
 * typically needed by ontology matchers.
 * 
 * Main elements:
  - Language: a three-letter ISO-639-3 language code, e.g., "eng", "ita"
  - Domain:   an English string label identifying a domain, e.g., "Medicine"
  - Word:     a word (canonical form of a word), e.g., "play", "jouer"
  - WordRelation: a lexical relation between two words, e.g., car SYNONYM automobile
  - Concept:  a string-based identifier of a meaning (concept/sense/synset/etc.)
              within a specific resource
  - ConceptRelation: a semantic relation between two concepts,
              e.g., concept_of_vehicle HYPERNYM_OF concept_of_car
 * 
 * 
 * @author Gábor Bella
 */
public interface IDivAPI {
    
    public abstract class Relation {
        
        public String  name;
        public boolean isReflexive;
        public boolean isSymmetric;
        public boolean isTransitive;
        
        public Relation(String name, boolean isReflexive, boolean isSymmetric, boolean isTransitive) {
            this.name = name;
            this.isReflexive = isReflexive;
            this.isSymmetric = isSymmetric;
            this.isTransitive = isTransitive;
        }
        
        public boolean isReflexive() { return isReflexive; }
        public boolean isSymmetric() { return isSymmetric; }
        public boolean isTransitive() { return isTransitive; }
        @Override
        public String toString() { return name; }
    }
    
    public class WordRelation extends Relation {
        public WordRelation(String name, boolean isReflexive, boolean isSymmetric, boolean isTransitive) {
            super(name, isReflexive, isSymmetric, isTransitive);
        }
    };
   
    public class ConceptRelation extends Relation {
        public ConceptRelation(String name, boolean isReflexive, boolean isSymmetric, boolean isTransitive) {
            super(name, isReflexive, isSymmetric, isTransitive);
        }
    };

    public final ConceptRelation CONCEPT_EQUIVALENCE = new ConceptRelation("CONCEPT_EQUIVALENCE", true, true, true);
    public final ConceptRelation SENSE_AXIS = new ConceptRelation("SENSE_AXIS", true, true, true);
    public final ConceptRelation CONCEPT_HYPERNYMY = new ConceptRelation("CONCEPT_HYPERNYMY", false, false, true);
    public final ConceptRelation CONCEPT_HYPONYMY = new ConceptRelation("CONCEPT_HYPONYMY", false, false, true);
    public final ConceptRelation CONCEPT_HOLONYMY = new ConceptRelation("CONCEPT_HOLONYMY", false, false, true);
    public final ConceptRelation CONCEPT_MERONYMY = new ConceptRelation("CONCEPT_MERONYMY", false, false, true);
    public final ConceptRelation CONCEPT_SIMILARITY= new ConceptRelation("CONCEPT_SIMILARITY", true, true, false);
    public final ConceptRelation CONCEPT_RELATEDNESS = new ConceptRelation("CONCEPT_RELATEDNESS", false, true, true);
    
    public final WordRelation WORD_SYNONYMY= new WordRelation("WORD_SYNONYMY", false, true, true);
    public final WordRelation WORD_ANTONYMY= new WordRelation("WORD_ANTONYMY", false, true, false);
    public final WordRelation WORD_SIMILARITY= new WordRelation("WORD_SIMILARITY", false, true, false);
    public final WordRelation WORD_RELATEDNESS = new WordRelation("WORD_RELATEDNESS", false, true, false);

    /*******************************************************
     * Methods on words.
     *******************************************************/
    
    /**
     * Return all words that are related to the input word through the relation specified,
     * in the given language and within the given domain.
     * @param language  the language of the word
     * @param domain    the domain to which the request is constrained,
     *                  null means no restriction
     * @param word      the word for which synonyms will be returned
     * @param rel       the relation kind by which relatedness is evaluated
     * @return          a set of related words
     */
    Set<String> getRelatedWords(String language, Domain domain, String word, WordRelation rel);

    /**
     * Return all words that are related to the input word through the relation specified,
     * in the given language and within the given domain, with the degree of relatedness
     * provided in the output.
     * 
     * @param language  the language of the word
     * @param domain    the domain to which the request is constrained,
     *                  null means no restriction
     * @param word      the word for which synonyms will be returned
     * @param rel       the relation kind by which relatedness is evaluated
     * @return          a set of related words, each one weighted with a relatedness
     *                  metric between 0 and 1
     */
    Map<String,Double> getRelatedWordsWeighted(String language, Domain domain, String word, WordRelation rel);

    /**
     * Return word relations that hold between the two words in input,
     * in the given language and within the given domain.
     * @param language  the language of the word
     * @param domain    the domain to which the request is constrained,
     *                  null means no restriction
     * @param word1     the first word
     * @param word2     the second word
     * @return          word relations between word1 and word2,
     *                  or null if no relation holds
     */
    Set<WordRelation> getRelations(String language, Domain domain, String word1, String word2);

    /**
     * Return weighted word relations between the two words in input,
     * in the given language and within the given domain.
     * @param language  the language of the word
     * @param domain    the domain to which the request is constrained,
     *                  null means no restriction
     * @param word1     the first word
     * @param word2     the second word
     * @return          weighted word relations that hold between word1 and word2,
     *                  or null if no relation holds
     */
    Map<WordRelation, Double> getRelationsWeighted(String language, Domain domain, 
                                                   String word1, String word2);

    /**
     * Return the languages in which the resource provides lexicalisations
     * for the given word and in the given domain.
     * @param domain    the domain to which the request is constrained,
     *                  null means no restriction
     * @param word      the word being queried
     * @return          the set of languages that have the word in the resource
     */
    Set<String> getLanguages(Domain domain, String word);

    /**
     * Return the domains in which the resource provides lexicalisations
     * for the given word and in the given language.
     * @param language  the language to which the request is constrained,
     *                  null means no restriction
     * @param word      the word being queried
     * @return          the set of domains that have the word in the resource
     */
    Set<Domain> getDomains(String language, String word);
    
    /**
     * Return the degree by which a word in a given language belongs
     * to each domain provided in input.
     * @param language  the language to which the request is constrained,
     *                  null means no restriction
     * @param word      the word being queried
     * @param domains   the set of domains with respect to which
     *                  the word will be evaluated
     * @return          the set of domains that have the word in the resource
     */
    Map<Domain,Double> getDomainsWeighted(String language, String word, Set<Domain> domains);

    
    /*******************************************************
     * Methods on concepts.
     *******************************************************/

    Set<Concept> getConcepts(String language, Domain domain, String word);
    Map<Concept,Double> getConceptsWeighted(String language, Domain domain, String word);
    Set<Concept> getConstrainedConcepts(String language, Domain domain, 
                                        String word, Concept hypernymConcept);
    Map<Concept,Double> getConstrainedConceptsWeighted(String language, Domain domain,
                                        String word, Concept hypernymConcept);
    Set<String> getWords(String language, Concept concept);
    Map<String,Double> getWordsWeighted(String language, Concept concept);
    String getGloss(String language, Concept concept);
    Set<Concept> getRelatedConcepts(Concept concept, Set<ConceptRelation> relations);
    Map<Concept,Double> getRelatedConceptsWeighted(Concept concept, Set<ConceptRelation> relations);
    Set<ConceptRelation> getRelations(Concept c1, Concept c2);
    Map<ConceptRelation,Double> getRelationsWeighted(Concept c1, Concept c2);
    Set<String> getLanguages(Concept concept);
    Set<Domain> getDomains(Concept concept);
    Map<Domain,Double> getDomainsWeighted(Concept concept);

    Set<String> getLanguages();
    Set<Domain> getDomains();
}
