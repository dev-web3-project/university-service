package com.lms.university.config;

import com.lms.university.entity.*;
import com.lms.university.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class EPTStructureSeeder implements CommandLineRunner {

    private final EstablishmentRepository establishmentRepository;
    private final CycleRepository cycleRepository;
    private final ClassRepository classRepository;
    private final GenieRepository genieRepository;

    @Override
    public void run(String... args) throws Exception {
        // Créer l'EPT si pas existant
        if (establishmentRepository.count() == 0) {
            seedEPTStructure();
        }
    }

    private void seedEPTStructure() {
        // 1. Créer l'établissement EPT
        Establishment ept = new Establishment();
        ept.setName("École Polytechnique de Thiès");
        ept.setDescription("École d'ingénieurs de renommée nationale et internationale, spécialisée en sciences et technologies.");
        ept = establishmentRepository.save(ept);

        // 2. Créer les Cycles
        Cycle troncCommun = new Cycle();
        troncCommun.setName("Tronc Commun");
        troncCommun.setDescription("2 premières années de formation généraliste en sciences fondamentales.");
        troncCommun.setEstablishment(ept);
        troncCommun = cycleRepository.save(troncCommun);

        Cycle cycleIngenieur = new Cycle();
        cycleIngenieur.setName("Cycle Ingénieur");
        cycleIngenieur.setDescription("3 années de formation spécialisée pour le diplôme d'ingénieur.");
        cycleIngenieur.setEstablishment(ept);
        cycleIngenieur = cycleRepository.save(cycleIngenieur);

        // 3. Créer les Classes pour Tronc Commun
        com.lms.university.entity.Class tc1 = createAndSaveClass("TC1", "1ère année du tronc commun - formation généraliste (pas de génie)", troncCommun);
        com.lms.university.entity.Class tc2 = createAndSaveClass("TC2", "2ème année du tronc commun - 2ème semestre avec spécialisation", troncCommun);

        // 4. Créer les Classes pour Cycle Ingénieur
        com.lms.university.entity.Class dic1 = createAndSaveClass("DIC1", "1ère année du cycle ingénieur (Diplôme d'Ingénieur Civil)", cycleIngenieur);
        com.lms.university.entity.Class dic2 = createAndSaveClass("DIC2", "2ème année du cycle ingénieur", cycleIngenieur);
        com.lms.university.entity.Class dic3 = createAndSaveClass("DIC3", "3ème année du cycle ingénieur (PFE)", cycleIngenieur);

        // 5. Créer les 5 Génies
        List<com.lms.university.entity.Class> classesWithGenies = Arrays.asList(tc2, dic1, dic2, dic3);
        
        List<String> genieNames = Arrays.asList(
            "Génie Civil (GC)",
            "Génie Électromécanique (GEM)",
            "Génie Informatique et Télécommunications (GIT)",
            "Génie Aéronautique (GA)",
            "Génie Industriel (GI)"
        );
        
        List<String> genieShortNames = Arrays.asList("GC", "GEM", "GIT", "GA", "GI");

        for (com.lms.university.entity.Class classRef : classesWithGenies) {
            for (int i = 0; i < genieNames.size(); i++) {
                Genie genie = new Genie();
                String className = classRef.getName();
                String genieName = className + " " + genieShortNames.get(i);
                
                genie.setName(genieName);
                genie.setDescription(genieNames.get(i) + " - " + className);
                genie.setClassRef(classRef);
                genieRepository.save(genie);
            }
        }
    }

    private com.lms.university.entity.Class createAndSaveClass(String name, String description, Cycle cycle) {
        com.lms.university.entity.Class classRef = new com.lms.university.entity.Class();
        classRef.setName(name);
        classRef.setDescription(description);
        classRef.setCycle(cycle);
        return classRepository.save(classRef);
    }
}
