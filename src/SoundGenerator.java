import javax.sound.midi.*;

public class SoundGenerator {

    public static void generateDropSound() {
        try {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();

            MidiChannel[] channels = synthesizer.getChannels();
            MidiChannel channel = channels[0];

            channel.programChange(35); 

            channel.noteOn(60, 80); 
            Thread.sleep(200);    
            channel.noteOff(60);      
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
