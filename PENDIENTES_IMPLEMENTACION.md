1. Crear `Deporte` entity y `DeporteRepository`
2. Actualizar `UsuarioDeporte`: descomentar relación @ManyToOne y eliminar campo deporteId temporal
3. Crear `UsuarioDeporteRepository` con query `findUsuariosByDeporte()`
4. Completar lógica en `UsuarioServiceImpl.findUsuariosByDeporte()`
5. Crear endpoint en `UsuarioController`: `GET /api/usuarios/por-deporte?deporteId=1&nivel=INTERMEDIO`

FALTA IMPLEMENTAR (ESTAN DECLARADOS)
- `IUsuarioService.findUsuariosByHistorialPartidos(Long usuarioId)`
- `UsuarioServiceImpl.findUsuariosByHistorialPartidos()` - lanza UsupportedOperationException`

1. Recrear `Partido`, `PartidoRepository`
2. Crear `UsuarioPartido` y `UsuarioPartidoRepository`
3. Query compleja para encontrar usuarios que han jugado juntos
4. Completar lógica en `UsuarioServiceImpl.findUsuariosByHistorialPartidos()`
5. Crear endpoint: `GET /api/usuarios/{id}/compañeros-partidos`

Gestión de Deportes de Usuario
CREADOS SIN IMPLEMENTACION
- `agregarDeporteAUsuario()`
- `removerDeporteDeUsuario()`
- `getDeportesDeUsuario()`

Completar lógica de CRUD de `UsuarioDeporte`
Crear endpoints:
   - `POST /api/usuarios/{id}/deportes`
   - `DELETE /api/usuarios/{id}/deportes/{deporteId}`
   - `GET /api/usuarios/{id}/deportes`


src/main/java/com/pds/partidosapp/
├── model/entity/
│   ├── Deporte.java ❌
│   ├── Partido.java ❌
│   └── UsuarioPartido.java ❌
├── repository/
│   ├── DeporteRepository.java ❌
│   ├── PartidoRepository.java ❌
│   ├── UsuarioDeporteRepository.java ❌
│   └── UsuarioPartidoRepository.java ❌
├── dto/
│   ├── DeporteDTO.java ❌
│   ├── PartidoDTO.java ❌
│   └── UsuarioDeporteDTO.java ❌
└── controller/
    ├── DeporteController.java ❌
    └── PartidoController.java ❌
```


- Las validaciones ya están implementadas en todos los DTOs
- La entidad `UsuarioDeporte` usa `deporteId` temporal
- Todas las queries deben filtrar por `activo = true`
