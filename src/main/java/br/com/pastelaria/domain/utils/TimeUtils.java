package br.com.pastelaria.domain.utils;

public class TimeUtils {
    public static int toSeconds(String subcommand, int value) {
        return switch (subcommand.toLowerCase()) {
            case "seconds", "second", "s" -> value;
            case "minutes", "minute", "mins", "min", "m" -> value * 60;
            case "hours", "hour", "h" -> value * 3600;
            default -> throw new IllegalArgumentException("Subcomando inválido: " + subcommand);
        };
    }

    public static String formatSeconds(long totalSeconds) {
        return formatSeconds((int) totalSeconds);
    }

    public static String formatSeconds(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        StringBuilder sb = new StringBuilder();

        if (hours > 0) sb.append(hours).append(" hora").append(hours > 1 ? "s" : "");
        if (minutes > 0) {
            if (!sb.isEmpty()) sb.append(" ");
            sb.append(minutes).append(" minuto").append(minutes > 1 ? "s" : "");
        }
        if (seconds > 0 || sb.isEmpty()) {
            if (!sb.isEmpty()) sb.append(" e ");
            sb.append(seconds).append(" segundo").append(seconds > 1 ? "s" : "");
        }

        return sb.toString();
    }
}
