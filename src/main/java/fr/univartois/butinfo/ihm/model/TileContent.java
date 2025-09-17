package fr.univartois.butinfo.ihm.model;

/**
 * L'énumération des différents contenus possibles d'une tuile.
 */
public enum TileContent implements ITileContent {
    /** Pelouse (franchissable, détruisible). */
    LAWN("lawn", true, true),

    /** Mur de briques (non franchissable, détruisible). */
    BRICK_WALL("bricks", false, true),

    /** Mur solide (non franchissable, indétruisible). */
    SOLID_WALL("wall", false, false);

    private final String name;
    private final boolean empty;
    private final boolean destroyableByExplosion;

    TileContent(String name, boolean empty, boolean destroyableByExplosion) {
        this.name = name;
        this.empty = empty;
        this.destroyableByExplosion = destroyableByExplosion;
    }

    /** Nom utilisé pour charger l’image correspondante (ex: "lawn.png"). */
    public String getName() {
        return name;
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }

    /** @return true si une explosion peut détruire cet élément. */
    public boolean isDestroyableByExplosion() {
        return destroyableByExplosion;
    }
}
