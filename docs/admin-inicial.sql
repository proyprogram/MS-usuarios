-- Script para dejar un usuario administrador inicial en usuarios_db.
-- La clave en texto plano es: Admin123*
-- Ejecutar contra la fila ya existente con correo = 'admin@plazoleta.com'
-- (ajustar el WHERE si el correo del admin inicial es otro).

UPDATE usuario
SET clave = '$2a$10$4P/oRHfXD7.kIUV5tpH7nOX4rF7/anIMI9afXumjqvIrKYaYjfKn6',  -- Admin123*
    rol   = 'ADMINISTRADOR'
WHERE correo = 'admin@plazoleta.com';
