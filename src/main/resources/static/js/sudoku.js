var SIZE = 9;
var EMPTY = 0;
var numTot = SIZE * SIZE;

$(document).ready(function () {

    $('#sudokuForm').validate({
        submitHandler: function (form) {
            showWait();
            return true;
        }
    });

    var sudokuAjaxOut = new Object();
    sudokuAjaxOut.solved = null;
    sudokuAjaxOut.msg = null;
    evaluateSolve(sudokuAjaxOut);

    $('#sudokuSubmit').click(function (e) {

        var validate = $('#sudokuForm').validate();
        var errors = validate.numberOfInvalids();
        if (errors != null && errors == 0) {
            validateSudoku();
        } else {
            e.preventDefault();
        }
    });

    $('#sudokuHelp').click(function () {
        helpMe();
    });

    $('#sudokuReset').click(function () {
        customReset();
    });
});

function evaluateSolve(sudokuAjaxOut) {

    var checkSolved = sudokuAjaxOut.solved;

    //console.log('checkSolved: ' + checkSolved);
    if (checkSolved === true || checkSolved === false) {
        var msg;
        if (sudokuAjaxOut.msg == '' || sudokuAjaxOut.msg == null) {
            if (checkSolved) {
                msg = 'Good Job!';
            } else {
                msg = 'Wrong!'
            }
        } else {
            msg = sudokuAjaxOut.msg;
        }

        $('#textValuate').text(msg);
        $('#textValuate').val(msg);

        if (checkSolved === true) {
            $('#sudokuSolve').prop('disabled', true);
            $('#sudokuHelp').prop('disabled', true);
        }

        $('#myModal').modal('show');
    }
}

function getSudokuTable() {

    var columns;
    var rows = [];

    var y, x;
    for (y = 0; y < SIZE; y++) {
        columns = [];
        for (x = 0; x < SIZE; x++) {
            var value = $('#' + x + '_' + y).val();
            var disabled = $('#' + x + '_' + y).prop('disabled');
            if (value == '' || value == null) {
                value = $('#' + x + '_' + y).text();
                if (value == '' || value == null) {
                    value = EMPTY;
                }
            }
            var cell = {
                "cell": value,
                "disabled" : disabled
            };
            columns.push(cell);
        }
        var column = {
            "columns": columns
        };
        rows.push(column);
    }

    var boardId = $('#boardId').val();
    var difficulty = $('#difficulty').val();

    var inputData = {
        "rows": rows,
        "difficulty": difficulty,
        "boardId": boardId
    };

    return inputData;
}

function validateSudoku() {

    var inputData = getSudokuTable();

    showWait();

    ajaxPost(baseUrl + 'rest/sudoku/validate', inputData, function (data) {
        evaluateSolve(data);
        hideWait();
    });
}

function helpMe() {

    var inputData = getSudokuTable();

    showWait();

    ajaxPost(baseUrl + 'rest/sudoku/helpMe', inputData, function (data) {
        evaluateHelp(data);
        hideWait();
    });
}

function evaluateHelp(sudokuTable) {

    var x, y = 0, cnt = 0;

    $.each(sudokuTable.rows, function () {
        x = 0;
        $.each(this.columns, function () {
            //console.log("x:" + x + ", y: " + y + ", value old:" + $('#' + x + '_' + y).val() + ", value new: " + this.cell);
            var valCell = this.cell;
            if (valCell != null && valCell != '' && valCell != EMPTY) {
                $('#' + x + '_' + y).val(valCell);
                $('#' + x + '_' + y).prop('disabled', this.disabled);
                cnt++;
            }
            x++;
        });
        y++;
    });

    //alla fine controllo se ho completato tutto il puzzle, e nel caso disabilito il tasto
    if (cnt == numTot) {
        $('#sudokuHelp').prop('disabled', true);
    }
}

function customReset() {
    $('input').not('[readonly],[disabled],:hidden,:button').val('');
    $('#sudokuSolve').prop('disabled', false);
    $('#sudokuHelp').prop('disabled', false);
}