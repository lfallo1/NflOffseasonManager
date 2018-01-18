package com.lancefallon.usermgmt.player.model;

public class PlayerNoteTag {

    private Integer id;
    private PlayerNote playerNote;
    private NoteTag noteTag;

    public PlayerNoteTag() {
    }

    public PlayerNoteTag(Integer id, PlayerNote playerNote, NoteTag noteTag) {
        this.id = id;
        this.playerNote = playerNote;
        this.noteTag = noteTag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PlayerNote getPlayerNote() {
        return playerNote;
    }

    public void setPlayerNote(PlayerNote playerNote) {
        this.playerNote = playerNote;
    }

    public NoteTag getNoteTag() {
        return noteTag;
    }

    public void setNoteTag(NoteTag noteTag) {
        this.noteTag = noteTag;
    }
}
