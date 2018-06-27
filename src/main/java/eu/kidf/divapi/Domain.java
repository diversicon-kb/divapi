package eu.kidf.divapi;

/**
 * The hierarchy of domains used by the DivAPI.
 * DivAPI implementations should map any resource-specific domain representations
 * to this hierarchy.
 *
 * @author Gábor Bella
 */
public enum Domain {
    PHILOSOPHY_PSYCHOLOGY("", 1),
    RELIGION("", 2),
    SOCIAL_SCIENCES("", 3),
    KNOWLEDGE_ORGANIZATION("", 4),
    NATURAL_SCIENCES("", 5),
        ENVIRONMENTAL_SCIENCES("", 50),
        MATHEMATICS("", 51),
        ASTRONOMY("", 52),
        PHYSICS("", 53),
        CHEMISTRY("", 54),
        EARTH_SCIENCES("", 55),
        PALAEONTOLOGY("", 56),
        BIOLOGICAL_SCIENCES("", 57),
        BOTANY("", 58),
        ZOOLOGY("", 59),
    APPLIED_SCIENCES("", 6),
        BIOTECHNOLOGY("", 60),
        MEDICAL_SCIENCES("", 61),
        HEALTHCARE("", 61),
        ENGINEERING("", 62),
        TECHNOLOGY("", 62),
        AGRICULTURE("", 63),
        HOME("", 64),
        COMMUNICATION_TRANSPORT_BUSINESS("", 65),
        CHEMICAL_TECHNOLOGY("", 66),
        TRADES_CRAFTS("", 67),
        INDUSTRIES_FOR_ASSEMBLED_ARTICLES("", 68),
        BUILDING("", 69),
    ARTS_RECREATION("", 7),
        PHYSICAL_PLANNING("", 71),
        ARCHITECTURE("", 72),
        PLASTIC_ARTS("", 73),
        DESIGN("", 74),
        PAINTING("", 75),
        GRAPHIC_ART("", 76),
        PHOTOGRAPHY("", 77),
        MUSIC("", 78),
        RECREATION("", 79),
            CINEMA("", 791),
            THEATRE("", 792),
            THEATER("", 792),
            DANCE("", 793),
            BOARD_GAMES("", 794),
            SPORT("", 796),
            WATER_AERIAL_SPORTS("", 797),
            RIDING("", 798),
            FISHING_HUNTING("", 799),
    LANGUAGE_LITERATURE("", 8),
    GEOGRAPHY_HISTORY("", 9);

    /* a short description of the domain */
    private String description;
    /* an integer identifier of the domain, when divided by 10 the identifier
       of the parent domain should be obtained */
    private Integer order;


    private Domain(String description, Integer order) {
        this.description = description;
        this.order = order;
    }

    public String getDescription() { return description; }
    public Integer getOrder() { return order; }
    public boolean equals(Domain d) {
        return d.getOrder().equals(order);
    }

    public static Domain getDomain(String domainName) {
        domainName = domainName.toUpperCase().replace(' ', '_');
        try {
            return Domain.valueOf(domainName);
        } catch(IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Return whether the domain given in input is subdomain of this domain.
     * @param d     the potential subdomain
     * @return      true if d is a subdomain, false otherwise
     */
    public boolean isSubDomain(Domain d) {
        Integer myOrder = order;
        Integer superOrder = d.getOrder();
        if (myOrder <= superOrder) {
            return false;
        }
        do {
            myOrder = myOrder / 10;
            if (myOrder.equals(superOrder)) {
                return true;
            }
        } while(myOrder > 0);
        return false;
    }
}

