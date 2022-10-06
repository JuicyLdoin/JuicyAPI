package net.juicy.api.server;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "servers")
public class JuicyServer {

    @Id
    String name;

    int players;
    int maxPlayers;

    @Enumerated(EnumType.STRING)
    JuicyServerStatus status;
    @Enumerated(EnumType.STRING)
    JuicyServerState state;

    @Transient
    boolean updatable;

}