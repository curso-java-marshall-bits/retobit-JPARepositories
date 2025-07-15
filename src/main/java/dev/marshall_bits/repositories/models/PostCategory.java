package dev.marshall_bits.repositories.models;

public enum PostCategory {
    TECHNOLOGY("Tecnología", "#3B82F6"),
    PROGRAMMING("Programación", "#10B981"),
    TUTORIAL("Tutorial", "#8B5CF6"),
    NEWS("Noticias", "#F59E0B"),
    REVIEW("Reseña", "#EF4444"),
    OPINION("Opinión", "#6B7280"),
    GUIDE("Guía", "#06B6D4"),
    DISCUSSION("Discusión", "#84CC16"),
    ANNOUNCEMENT("Anuncio", "#F97316"),
    OTHER("Otro", "#64748B");

    private final String displayName;
    private final String colorCode;

    PostCategory(String displayName, String colorCode) {
        this.displayName = displayName;
        this.colorCode = colorCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getColorCode() {
        return colorCode;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
