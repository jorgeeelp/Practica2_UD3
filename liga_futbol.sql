-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 06-02-2018 a las 15:35:21
-- Versión del servidor: 5.5.32
-- Versión de PHP: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `liga_futbol`
--
CREATE DATABASE IF NOT EXISTS `liga_futbol` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `liga_futbol`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipos`
--

DROP TABLE IF EXISTS `equipos`;
CREATE TABLE IF NOT EXISTS `equipos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `patrocinador` varchar(50) NOT NULL,
  `presupuesto` float NOT NULL,
  `categoria` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=20 ;

--
-- Volcado de datos para la tabla `equipos`
--

INSERT INTO `equipos` (`id`, `nombre`, `patrocinador`, `presupuesto`, `categoria`) VALUES
(1, 'Barca', 'bwin', 5000, 'profesional'),
(3, 'Athleti', 'Patricinio basico', 1, 'Novato'),
(4, 'Athletico', 'ribus', 345, 'gfd'),
(5, 'Sevilla', 'nike', 0, 'b'),
(8, 'Cultural', '', 3, ''),
(9, 'Murcia', '', 0.3, ''),
(10, 'Levante', 'asdg', -3445, 'grewgr'),
(11, 'Celta', '', 0, ''),
(12, 'Lleida', 'Jeque', 34, 'gwrhweh'),
(13, 'Zaragoza', '', 0, ''),
(14, 'Huesca', 'CR7', 34, 'grewrg'),
(15, 'Malaga', '', 0, ''),
(16, 'Granada', 'asdg', 0, 'sdgfhsdh'),
(17, 'Ponfe', ' ', 0, ' ');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipo_partido`
--

DROP TABLE IF EXISTS `equipo_partido`;
CREATE TABLE IF NOT EXISTS `equipo_partido` (
  `id_equipo` int(10) unsigned NOT NULL DEFAULT '0',
  `id_partido` int(10) unsigned NOT NULL DEFAULT '0',
  `condicion` enum('local','visitante') DEFAULT NULL,
  `goles` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`id_equipo`,`id_partido`),
  KEY `id_equipo` (`id_equipo`),
  KEY `id_partido` (`id_partido`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `equipo_partido`
--

INSERT INTO `equipo_partido` (`id_equipo`, `id_partido`, `condicion`, `goles`) VALUES
(1, 1, NULL, NULL),
(4, 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jugadores`
--

DROP TABLE IF EXISTS `jugadores`;
CREATE TABLE IF NOT EXISTS `jugadores` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `dorsal` tinyint(3) unsigned DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `id_equipo` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_equipo` (`id_equipo`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=26 ;

--
-- Volcado de datos para la tabla `jugadores`
--

INSERT INTO `jugadores` (`id`, `nombre`, `apellidos`, `dorsal`, `fecha_nacimiento`, `id_equipo`) VALUES
(1, 'Romario', 'Da Sousa', 3, '2017-10-11', 1),
(3, 'Messias', 'Salvador', 5, '2018-02-14', 1),
(4, 'Ronaldo', 'Da Faria', 23, '2013-02-22', 1),
(5, 'Jorge', 'Martin', 4, '2018-02-16', 3),
(8, 'Serresiete', 'Penaldo', 4, '2018-02-02', 1),
(9, 'Pedro', 'Delgado', 12, '2018-02-02', 1),
(15, 'Jony', 'cash', 3, '2018-04-04', 4),
(16, 'Francisco', 'Ibañez', 56, '2018-02-15', 4),
(18, 'Juanjo', 'Diaz', 3, '2018-02-24', 4),
(20, 'Jorge', 'Rutherford', 4, '2018-02-16', 4),
(25, 'Ferraro', 'Cuaresma', 67, '2018-02-16', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jugador_partido`
--

DROP TABLE IF EXISTS `jugador_partido`;
CREATE TABLE IF NOT EXISTS `jugador_partido` (
  `id_jugador` int(10) unsigned NOT NULL DEFAULT '0',
  `id_partido` int(10) unsigned NOT NULL DEFAULT '0',
  `goles` tinyint(3) unsigned DEFAULT '0',
  PRIMARY KEY (`id_jugador`,`id_partido`),
  KEY `id_jugador` (`id_jugador`),
  KEY `id_partido` (`id_partido`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `jugador_partido`
--

INSERT INTO `jugador_partido` (`id_jugador`, `id_partido`, `goles`) VALUES
(1, 1, 0),
(8, 1, 0),
(9, 1, 0),
(15, 1, 0),
(16, 1, 0),
(18, 1, 0),
(20, 1, 0),
(25, 1, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partidos`
--

DROP TABLE IF EXISTS `partidos`;
CREATE TABLE IF NOT EXISTS `partidos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `arbitro` varchar(50) DEFAULT 'Sin arbitro',
  `campo` varchar(50) DEFAULT 'Sin campo',
  `incidencias` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `partidos`
--

INSERT INTO `partidos` (`id`, `fecha`, `arbitro`, `campo`, `incidencias`) VALUES
(1, '2018-05-03 20:35:00', 'Mateu', 'Mestala', 'Bengalas, mecheros');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `equipo_partido`
--
ALTER TABLE `equipo_partido`
  ADD CONSTRAINT `equipo_partido_ibfk_1` FOREIGN KEY (`id_equipo`) REFERENCES `equipos` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `equipo_partido_ibfk_2` FOREIGN KEY (`id_partido`) REFERENCES `partidos` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Filtros para la tabla `jugadores`
--
ALTER TABLE `jugadores`
  ADD CONSTRAINT `jugadores_ibfk_1` FOREIGN KEY (`id_equipo`) REFERENCES `equipos` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Filtros para la tabla `jugador_partido`
--
ALTER TABLE `jugador_partido`
  ADD CONSTRAINT `jugador_partido_ibfk_1` FOREIGN KEY (`id_jugador`) REFERENCES `jugadores` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `jugador_partido_ibfk_2` FOREIGN KEY (`id_partido`) REFERENCES `partidos` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
