package net.juicy.api.server;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
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

}