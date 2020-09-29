package main;

public class TexTags {
    private String begin;
    private String end;

    public TexTags() {
        begin = "";
        end = "";
    }

    public TexTags(String begin, String end) {
        this.begin = begin;
        this.end = end;
    }

    public String getBegin() {
        return begin;
    }

    public String getEnd() {
        return end;
    }
}