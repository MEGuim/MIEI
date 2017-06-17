USE `gestor_eleicoes`;
DROP procedure IF EXISTS `DELETE_ELEICAO_E_DADOS`;

DELIMITER $$
USE `gestor_eleicoes`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `DELETE_ELEICAO_E_DADOS`(in id_eleicao_apagar INT)
BEGIN

SET FOREIGN_KEY_CHECKS=0;
DELETE FROM Lista WHERE id_lista IN (SELECT * FROM (SELECT Lista_id_lista FROM EleicaoListaCandidato WHERE id_eleicao_apagar=Eleicao_id_eleicao) AS p);
DELETE FROM EleicaoListaCandidato WHERE id_eleicao_apagar=Eleicao_id_eleicao;
DELETE FROM Eleicao WHERE id_eleicao_apagar=id_eleicao;
SET FOREIGN_KEY_CHECKS=1;

END$$

DELIMITER ;

