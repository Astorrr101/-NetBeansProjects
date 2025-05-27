-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-07-2023 a las 06:00:11
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
-- Base de datos: `coductores`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carros`
--

CREATE TABLE `carros` (
  `id_carros` int(11) NOT NULL,
  `placa` varchar(10) NOT NULL,
  `descripcion` varchar(75) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `carros`
--

INSERT INTO `carros` (`id_carros`, `placa`, `descripcion`) VALUES
(1, 'ABC123', 'Carro sedán de color rojo'),
(2, 'DEF456', 'Vehículo utilitario deportivo de color plateado'),
(3, 'GHI789', 'Camioneta de carga con capacidad para transporte pesado'),
(4, 'JKL012', 'Carro compacto de cinco puertas y excelente eficiencia en el consumo de com'),
(5, 'MNO345', 'Carro deportivo de dos puertas con un motor potente'),
(6, 'PQR678', 'Carro descapotable de color negro con interiores de cuero'),
(7, 'STU901', 'Vehículo familiar espacioso con capacidad para siete pasajeros'),
(8, 'VWX234', 'Camioneta de cabina doble y caja abierta para carga'),
(9, 'YZA567', 'Carro completamente eléctrico con cero emisiones y carga rápida'),
(10, 'BCD890', 'Vehículo todoterreno con tracción en las cuatro ruedas y suspensión elevada');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `conductor`
--

CREATE TABLE `conductor` (
  `id_conductor` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `licencia` varchar(50) NOT NULL,
  `cuidad` varchar(50) NOT NULL,
  `tipo_auto` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `conductor`
--

INSERT INTO `conductor` (`id_conductor`, `nombre`, `licencia`, `cuidad`, `tipo_auto`) VALUES
(1223, 'angel', 'todo', 'SFY', 'Mecánico');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `d_conductor`
--

CREATE TABLE `d_conductor` (
  `id_dconductor` int(11) NOT NULL,
  `id_conductor` int(11) NOT NULL,
  `id_carro` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `carros`
--
ALTER TABLE `carros`
  ADD PRIMARY KEY (`id_carros`);

--
-- Indices de la tabla `d_conductor`
--
ALTER TABLE `d_conductor`
  ADD PRIMARY KEY (`id_dconductor`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `carros`
--
ALTER TABLE `carros`
  MODIFY `id_carros` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `d_conductor`
--
ALTER TABLE `d_conductor`
  MODIFY `id_dconductor` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
