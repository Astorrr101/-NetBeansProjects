-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-07-2023 a las 21:13:11
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
-- Base de datos: `matricula`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno`
--

CREATE TABLE `alumno` (
  `AlumnoCode` int(5) NOT NULL,
  `AlumnoName` varchar(50) NOT NULL,
  `AlumnoTel` varchar(16) NOT NULL,
  `AlumnoDir` varchar(200) NOT NULL,
  `AlumnoEmail` varchar(100) NOT NULL,
  `AlumnoGene` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `alumno`
--

INSERT INTO `alumno` (`AlumnoCode`, `AlumnoName`, `AlumnoTel`, `AlumnoDir`, `AlumnoEmail`, `AlumnoGene`) VALUES
(1, 'Angel Ramon', '123131', 'San Francisco', 'Angel@yahoo.com', 'Masculino'),
(2, 'ramon', '1231312', 'SPS', 'ramon@gmail.com', 'Masculino'),
(3, 'juana', '12312341', 'SPS', 'juana@gmail.com', 'Femenino'),
(4, 'Gabriel', '42311', 'SPS', 'GAbriel@hotmail.com', 'Masculino'),
(5, 'carlos', '13092', 'SPS', 'carlos@gmai.com', 'Masculino'),
(6, 'rosa', '1231231', 'SPS', 'rosa@gmail.com', 'Femenino');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `UserID` int(11) NOT NULL,
  `Username` varchar(50) DEFAULT NULL,
  `UserPassw` varchar(50) DEFAULT NULL,
  `Estatus` int(1) DEFAULT NULL,
  `token` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`UserID`, `Username`, `UserPassw`, `Estatus`, `token`) VALUES
(1, 'Angel', 'pas123', 1, 'HTo92'),
(2, 'Ramon', 'secret12', 0, 'HTo92'),
(3, 'MikeJohnson', 'pass1234', 0, 'HTo92');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`AlumnoCode`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`UserID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alumno`
--
ALTER TABLE `alumno`
  MODIFY `AlumnoCode` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
