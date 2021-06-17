const express = require('express');
const router = express.Router();

const ResourceController = require('../Controllers/resourceController');
// const authHelper = require('../Helpers/AuthHelper');

router.post('/create-resource', ResourceController.CreateResource);
router.get('/get-all-resources', ResourceController.getAllResources);
router.put('/update-resource/:id', ResourceController.updateResource);
router.delete('/delete-resource/:id', ResourceController.deleteResource);

module.exports = router;