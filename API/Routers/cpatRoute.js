const express = require('express');
const router = express.Router();

const CpatController = require('../Controllers/cpatController');
// const authHelper = require('../Helpers/AuthHelper');

router.post('/create-cpat', CpatController.CreateCpat);
router.get('/get-all-cpats', CpatController.getAllCpats);
router.put('/update-cpat/:id', CpatController.updateCpat);
router.delete('/delete-cpat/:id', CpatController.deleteCpat);

module.exports = router;