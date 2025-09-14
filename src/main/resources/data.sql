
CREATE TABLE IF NOT EXISTS livros (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    ano INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    perfil VARCHAR(20) NOT NULL DEFAULT 'USER',
    ativo BOOLEAN NOT NULL DEFAULT true
);

INSERT INTO livros (titulo, autor, ano) 
SELECT * FROM (VALUES 
    ('1984', 'George Orwell', 1949),
    ('Dom Casmurro', 'Machado de Assis', 1899),
    ('O Cortiço', 'Aluísio Azevedo', 1890),
    ('Senhora', 'José de Alencar', 1875),
    ('O Guarani', 'José de Alencar', 1857),
    ('Memórias Póstumas de Brás Cubas', 'Machado de Assis', 1881),
    ('Casa Grande e Senzala', 'Gilberto Freyre', 1933),
    ('Capitães da Areia', 'Jorge Amado', 1937),
    ('Quincas Borba', 'Machado de Assis', 1891),
    ('Iracema', 'José de Alencar', 1865)
) AS tmp(titulo, autor, ano)
WHERE NOT EXISTS (SELECT 1 FROM livros LIMIT 1);
