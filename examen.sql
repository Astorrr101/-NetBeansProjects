-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-06-2023 a las 05:35:27
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `examen`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registro`
--

CREATE TABLE `registro` (
  `codigo` varchar(50) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  `email` varchar(50) NOT NULL,
  `mes` varchar(20) NOT NULL,
  `anio` int(4) NOT NULL,
  `genero` varchar(20) NOT NULL,
  `licencia` varchar(20) NOT NULL,
  `hijos` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `registro`
--

INSERT INTO `registro` (`codigo`, `nombre`, `direccion`, `email`, `mes`, `anio`, `genero`, `licencia`, `hijos`) VALUES
('121', '12', 'asdq', 'we1', 'abril', 1990, ' Masculino', ' Pesada', 'tiene hijos 12'),
('12', 'qwe', 'qwe', 'qwe', 'marzo', 1990, ' Masculino', ' Pesada', 'tiene hijos 1'),
('1', 'angel', 'San Francico', 'angel@', 'diciembre', 2002, ' Masculino', ' Moto', ' 0'),
('2', 'ramon', 'sps', 'ramon@', 'julio', 1998, ' Masculino', ' Pesada', ' 5');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
