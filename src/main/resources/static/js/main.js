//$.fn.datepicker.defaults.format = 'dd/mm/yyyy';

$(document).ready(function () {

    $('.wait').click(function () {
        showWait();
    });

    //Check to see if the window is top if not then display button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 100) {
            $('.scrollToTop').fadeIn();
        } else {
            $('.scrollToTop').fadeOut();
        }
    });

    //Click event to scroll to top
    $('.scrollToTop').click(function () {
        $('html, body').animate({scrollTop: 0}, 800);
        return false;
    });

    $('.closeAlert').click(function () {
        $("#alertDiv").fadeOut();
    });


    $('.span-alert').css('font-family', $('body').css('font-family'));
});

/**
 * Funzioni per mostrare / nascondere una rotellina che gira con lo sfondo modal
 * generalmente usato dopo aver lanciato chiamate al server
 * N.B. per usare queste funzioni si deve utilizzare l'elemento box.html
 */
function showWait() {
    $('#box-overlay').show();
};

function hideWait() {
    $('#box-overlay').hide();
};

jQuery.extend(jQuery.validator.messages, {
    required: "Campo obbligatorio",
    remote: "Controlla questo campo",
    email: "Inserisci un indirizzo email valido",
    url: "Inserisci un indirizzo web valido",
    date: "Inserisci una data valida",
    dateISO: "Inserisci una data valida (ISO)",
    number: "Inserisci un numero valido",
    digits: "Inserisci solo numeri",
    creditcard: "Inserisci un numero di carta di credito valido",
    equalTo: "Il valore non corrisponde",
    extension: "Inserisci un valore con un&apos;estensione valida",
    maxlength: $.validator.format("Non inserire pi&ugrave; di {0} caratteri"),
    minlength: $.validator.format("Inserisci almeno {0} caratteri"),
    rangelength: $.validator.format("Inserisci un valore compreso tra {0} e {1} caratteri"),
    range: $.validator.format("Inserisci un valore compreso tra {0} e {1}"),
    max: $.validator.format("Inserisci un valore minore o uguale a {0}"),
    min: $.validator.format("Inserisci un valore maggiore o uguale a {0}"),
    nifES: "Inserisci un NIF valido",
    nieES: "Inserisci un NIE valido",
    cifES: "Inserisci un CIF valido",
    currency: "Inserisci una valuta valida"
});

var manageError = function (jqXHR, textStatus, errorThrown) {
    var status = jqXHR.status;
    // console.log(status + ": " + errorThrown);
    if (status == 0) {
        //caso in cui si fa una richiesta mentre ne esiste una pending
        //Non mostro il popup e lascio che risponde alla seconda richiesta
        return;
    }

    var res = jqXHR.responseText;

    $('#general-modal .modal-body').html(res);
    $('#general-modal').show();

    $('#general-modal button.close').click(function (event) {
        $('#general-modal').hide();
    });
};

function formatNumberCallback(toFormat) {
    return toFormat.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
};

$.datepicker.setDefaults($.datepicker.regional['it']);

function initiDatePickerWithStartDate(days) {
    var date = moment().add(days, 'days').format('DD/MM/YYYY');
    $('.datepicker').datepicker({
        language: 'it',
        autoclose: true,
        todayHighlight: true,
        startDate: date
    });
}

function initiDatePicker() {
    $('.datepicker').datepicker({
        dateFormat: 'dd/mm/yy',
        // changeMonth: true,
        changeYear: true,
        closeText: 'Chiudi',
        prevText: 'Prec',
        nextText: 'Succ',
        currentText: 'Oggi',
        monthNames: ['Gennaio', 'Febbraio', 'Marzo', 'Aprile', 'Maggio', 'Giugno', 'Luglio', 'Agosto', 'Settembre', 'Ottobre', 'Novembre', 'Dicembre'],
        monthNamesShort: ['Gen', 'Feb', 'Mar', 'Apr', 'Mag', 'Giu', 'Lug', 'Ago', 'Set', 'Ott', 'Nov', 'Dic'],
        dayNames: ['Domenica', 'Lunedì', 'Martedì', 'Mercoledì', 'Giovedì', 'Venerdì', 'Sabato'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mer', 'Gio', 'Ven', 'Sab'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Me', 'Gio', 'Ve', 'Sa'],
        // firstDay: 1
    });
}

function capitalize(str) {
    return str.toLowerCase().replace(/(^|\s)[a-z\u00E0-\u00FC]/g, function (letter) {
        return letter.toUpperCase();
    });
}

function checkOptionLength() {
    var maxLength = 100;
    $('select option').text(function (i, text) {
        if (text.length > maxLength) {
            return text.substr(0, maxLength) + '...';
        }
    });
}

// converte i bytes in misure leggibili
function bytesToSize(bytes, decimals) {
    if (bytes == 0) return '0 Bytes';
    var k = 1024,
        dm = decimals || 2,
        sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
}

function valorizzaSelect2(selectId, value) {
    $('#' + selectId).val(value).trigger('change'); //fix per select2
    //$('#' + selectId + ' option[value="' + value + '"]').attr('selected', 'selected');
}

var ajaxGet = function (url, data, callback) {
    return $.ajax({
        url: url,
        type: 'GET',

        //async: false,
        cache: false,
        processData: false,

        dataType: 'json',
        contentType: 'application/json',
        data: data,
        success: function (data) {
            if (callback) callback(data);
        },
        error: manageError
    });
};

var ajaxPost = function (url, data, callback) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: url,
        type: 'POST',

        async: true,
        cache: false,
        processData: false,

        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (data) {
            callback(data);
        },
        error: manageError
    });
};

function escapeHtml(val) {
    if (val == null) {
        return val;
    }
    var value = val
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
    return '<span aria-label="' + value + '">' + value + '</span>';
}