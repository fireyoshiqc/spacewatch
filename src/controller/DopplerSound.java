/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.InputStream;
import java.net.URL;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.Log;

/**
 *
 * @author 1331037
 */
public class DopplerSound {
    /**
     * The internal sound effect represent this sound
     */
    private Audio sound;

    /**
     * Create a new Sound
     *
     * @param in The location of the OGG or MOD/XM to load
     * @param ref The name to associate this stream
     * @throws SlickException Indicates a failure to load the sound effect
     */
    public DopplerSound(InputStream in, String ref) throws SlickException {
        SoundStore.get().init();

        try {
            if (ref.toLowerCase().endsWith(".ogg")) {
                sound = SoundStore.get().getOgg(in);
            } else if (ref.toLowerCase().endsWith(".wav")) {
                sound = SoundStore.get().getWAV(in);
            } else if (ref.toLowerCase().endsWith(".aif")) {
                sound = SoundStore.get().getAIF(in);
            } else if (ref.toLowerCase().endsWith(".xm") || ref.toLowerCase().endsWith(".mod")) {
                sound = SoundStore.get().getMOD(in);
            } else {
                throw new SlickException("Only .xm, .mod, .aif, .wav and .ogg are currently supported.");
            }
        } catch (Exception e) {
            Log.error(e);
            throw new SlickException("Failed to load sound: " + ref);
        }
    }

    /**
     * Create a new Sound
     *
     * @param url The location of the OGG or MOD/XM to load
     * @throws SlickException Indicates a failure to load the sound effect
     */
    public DopplerSound(URL url) throws SlickException {
        SoundStore.get().init();
        String ref = url.getFile();

        try {
            if (ref.toLowerCase().endsWith(".ogg")) {
                sound = SoundStore.get().getOgg(url.openStream());
            } else if (ref.toLowerCase().endsWith(".wav")) {
                sound = SoundStore.get().getWAV(url.openStream());
            } else if (ref.toLowerCase().endsWith(".aif")) {
                sound = SoundStore.get().getAIF(url.openStream());
            } else if (ref.toLowerCase().endsWith(".xm") || ref.toLowerCase().endsWith(".mod")) {
                sound = SoundStore.get().getMOD(url.openStream());
            } else {
                throw new SlickException("Only .xm, .mod, .aif, .wav and .ogg are currently supported.");
            }
        } catch (Exception e) {
            Log.error(e);
            throw new SlickException("Failed to load sound: " + ref);
        }
    }

    /**
     * Create a new Sound
     *
     * @param ref The location of the OGG or MOD/XM to load
     * @throws SlickException Indicates a failure to load the sound effect
     */
    public DopplerSound(String ref) throws SlickException {
        SoundStore.get().init();

        try {
            if (ref.toLowerCase().endsWith(".ogg")) {
                sound = SoundStore.get().getOgg(ref);
            } else if (ref.toLowerCase().endsWith(".wav")) {
                sound = SoundStore.get().getWAV(ref);
            } else if (ref.toLowerCase().endsWith(".aif")) {
                sound = SoundStore.get().getAIF(ref);
            } else if (ref.toLowerCase().endsWith(".xm") || ref.toLowerCase().endsWith(".mod")) {
                sound = SoundStore.get().getMOD(ref);
            } else {
                throw new SlickException("Only .xm, .mod, .aif, .wav and .ogg are currently supported.");
            }
        } catch (Exception e) {
            Log.error(e);
            throw new SlickException("Failed to load sound: " + ref);
        }
    }

    /**
     * Play this sound effect at default volume and pitch
     */
    public void play() {
        play(1.0f, 1.0f);
    }

    /**
     * Play this sound effect at a given volume and pitch
     *
     * @param pitch The pitch to play the sound effect at
     * @param volume The volumen to play the sound effect at
     */
    public void play(float pitch, float volume) {
        sound.playAsSoundEffect(pitch, volume * SoundStore.get().getSoundVolume(), false);
    }

    /**
     * Play a sound effect from a particular location
     *
     * @param x The x position of the source of the effect
     * @param y The y position of the source of the effect
     * @param z The z position of the source of the effect
     */
    public void playAt(float x, float y, float z) {
        playAt(1.0f, 1.0f, x, y, z);
    }

    /**
     * Play a sound effect from a particular location
     *
     * @param pitch The pitch to play the sound effect at
     * @param volume The volumen to play the sound effect at
     * @param x The x position of the source of the effect
     * @param y The y position of the source of the effect
     * @param z The z position of the source of the effect
     */
    public void playAt(float pitch, float volume, float x, float y, float z) {
        sound.playAsSoundEffect(pitch, volume * SoundStore.get().getSoundVolume(), false, x, y, z);
    }

    /**
     * Loop this sound effect at default volume and pitch
     */
    public void loop() {
        loop(1.0f, 1.0f);
    }

    /**
     * Loop this sound effect at a given volume and pitch
     *
     * @param pitch The pitch to play the sound effect at
     * @param volume The volumen to play the sound effect at
     */
    public void loop(float pitch, float volume) {
        sound.playAsSoundEffect(pitch, volume * SoundStore.get().getSoundVolume(), true);
    }

    /**
     *
     * @param pitch
     * @param volume
     * @return
     */
    public int pitchLoop(float pitch, float volume) {
        return sound.playAsSoundEffect(pitch, volume * SoundStore.get().getSoundVolume(), true);
    }
    
    /**
     *
     * @param pitch
     * @param volume
     * @param x
     * @param y
     * @param z
     * @return
     */
    public int pitchLoop(float pitch, float volume, float x, float y, float z) {
        return sound.playAsSoundEffect(pitch, volume * SoundStore.get().getSoundVolume(), true, x, y, z);
    }

    /**
     * Check if the sound is currently playing
     *
     * @return True if the sound is playing
     */
    public boolean playing() {
        return sound.isPlaying();
    }

    /**
     * Stop the sound being played
     */
    public void stop() {
        sound.stop();
    }
    
}
