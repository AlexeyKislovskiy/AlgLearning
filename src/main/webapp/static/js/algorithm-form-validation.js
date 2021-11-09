document.addEventListener('DOMContentLoaded', function () {
    input3x3();

    function changeOrder(first, v) {
        let g = ["L", "B", "D", "U", "F", "R", "X", "Y", "Z", "M", "S", "E", "W", " ", "2", "'"];

        let flag = 1;
        while (flag) {
            flag = 0;
            for (let j = 0; j < v.length; j++) {
                let s1 = "", s2 = "", w1 = "", z1 = "", z2 = "", flag1 = 0, ls = 0;
                for (let k = 0; k < 3; k++) {
                    if (v.charAt(j).toUpperCase() === g[first * 3 + k]) {
                        s1 = v.charAt(j);
                        if (first === 0) {
                            if (s1 === "D") s2 = "U";
                            if (s1 === "L") s2 = "R";
                            if (s1 === "B") s2 = "F";
                        } else {
                            if (s1 === "U") s2 = "D";
                            if (s1 === "R") s2 = "L";
                            if (s1 === "F") s2 = "B";
                        }
                        flag1 = 1;
                        break
                    }
                }
                let flag2 = 0;
                if (flag1 === 1) {
                    if (v.charAt(j + 1) === "w") {
                        w1 = "w";
                        if (v.charAt(j + 2) === " " && v.length > j + 2) {
                            z1 = "";
                            if (v.charAt(j + 3) === s2 && v.charAt(j + 4) === w1) {
                                if (v.charAt(j + 5) === " " && v.length > j + 5) {
                                    flag2 = 1;
                                    z2 = "";
                                    ls = 5;
                                } else if (v.charAt(j + 6) === " " && v.length > j + 6) {
                                    flag2 = 1;
                                    z2 = v.charAt(j + 5);
                                    ls = 6;
                                }
                            }
                        } else {
                            z1 = v.charAt(j + 2);

                            if (v.charAt(j + 4) === s2 && v.charAt(j + 5) === w1) {
                                if (v.charAt(j + 6) === " " && v.length > j + 6) {
                                    flag2 = 1;
                                    z2 = "";
                                    ls = 6;
                                } else if (v.charAt(j + 7) === " " && v.length > j + 7) {
                                    flag2 = 1;
                                    z2 = v.charAt(j + 6);
                                    ls = 7;
                                }
                            }

                        }
                    } else {
                        w1 = "";
                        if (v.charAt(j + 1) === " " && v.length > j + 1) {
                            z1 = "";
                            if (v.charAt(j + 2) === s2) {
                                if (v.charAt(j + 3) === " " && v.length > j + 3) {
                                    flag2 = 1;
                                    z2 = "";
                                    ls = 3;
                                } else if (v.charAt(j + 3) !== "w" && v.charAt(j + 4) === " " && v.length > j + 4) {
                                    flag2 = 1;
                                    z2 = v.charAt(j + 3);
                                    ls = 4;
                                }
                            }
                        } else {
                            z1 = v.charAt(j + 1);

                            if (v.charAt(j + 3) === s2) {
                                if (v.charAt(j + 4) === " " && v.length > j + 4) {
                                    flag2 = 1;
                                    z2 = "";
                                    ls = 4;
                                } else if (v.charAt(j + 4) !== "w" && v.charAt(j + 5) === " " && v.length > j + 5) {
                                    flag2 = 1;
                                    z2 = v.charAt(j + 4);
                                    ls = 5;
                                }
                            }
                        }
                    }
                    if (flag2 === 1) {
                        flag = 1;
                        if (w1 === "w") {
                            if (z1 === "2" && z2 === "2" || z1 === "" && z2 === "" || z1 === "'" && z2 === "'") {
                                v = v.substring(0, j) + s2 + z2 + " " + s1 + z1 + v.substring(j + ls + 1);
                            } else v = v.substring(0, j) + s2 + w1 + z2 + " " + s1 + w1 + z1 + v.substring(j + ls + 1);
                        } else v = v.substring(0, j) + s2 + w1 + z2 + " " + s1 + w1 + z1 + v.substring(j + ls + 1);
                        break;
                    }

                }
            }


        }

        return v;
    }

    function currentState(up, front, s) {
        let n;
        if (s.length === 1) n = 1;
        else if (s.charAt(1) === "2") n = 2;
        else n = 3;
        for (let i = 0; i < n; i++) {
            if (s.charAt(0) === "x") {
                let k = up;
                up = front;
                if (k === 1) front = 6;
                else if (k === 2) front = 3;
                else if (k === 3) front = 2;
                else if (k === 4) front = 5;
                else if (k === 5) front = 4;
                else front = 1;
            } else if (s.charAt(0) === "y") {
                if (up === 1 && front === 2 || up === 2 && front === 6 || up === 6 && front === 3 || up === 3 && front === 1) {
                    front = 5;
                } else if (up === 1 && front === 4 || up === 4 && front === 6 || up === 6 && front === 5 || up === 5 && front === 1) {
                    front = 2;
                } else if (up === 1 && front === 3 || up === 3 && front === 6 || up === 6 && front === 2 || up === 2 && front === 1) {
                    front = 4;
                } else if (up === 1 && front === 5 || up === 5 && front === 6 || up === 6 && front === 4 || up === 4 && front === 1) {
                    front = 3;
                } else if (up === 2 && front === 5 || up === 5 && front === 3 || up === 3 && front === 4 || up === 4 && front === 2) {
                    front = 1;
                } else front = 6;
            } else {
                if (up === 1 && front === 3 || up === 3 && front === 6 || up === 6 && front === 2 || up === 2 && front === 1) {
                    up = 5;
                } else if (up === 1 && front === 5 || up === 5 && front === 6 || up === 6 && front === 4 || up === 4 && front === 1) {
                    up = 2;
                } else if (up === 1 && front === 2 || up === 2 && front === 6 || up === 6 && front === 3 || up === 3 && front === 1) {
                    up = 4;
                } else if (up === 1 && front === 4 || up === 4 && front === 6 || up === 6 && front === 5 || up === 5 && front === 1) {
                    up = 3;
                } else if (up === 2 && front === 4 || up === 4 && front === 3 || up === 3 && front === 5 || up === 5 && front === 2) {
                    up = 1;
                } else up = 6;
            }
        }
        let ans = [up, front];
        return ans;
    }

    function optimalByState(up, front) {
        if (up === 1) {
            if (front === 2) return "";
            else if (front === 3) return "y2";
            else if (front === 4) return "y'";
            else return "y";
        } else if (up === 2) {
            if (front === 1) return "x y2";
            else if (front === 4) return "x y'";
            else if (front === 5) return "x y";
            else return "x";
        } else if (up === 3) {
            if (front === 1) return "x'";
            else if (front === 4) return "x' y'";
            else if (front === 5) return "x' y";
            else return "x' y2";
        } else if (up === 4) {
            if (front === 1) return "z y";
            else if (front === 2) return "z";
            else if (front === 3) return "z y2";
            else return "z y'";
        } else if (up === 5) {
            if (front === 1) return "z' y'";
            else if (front === 2) return "z'";
            else if (front === 3) return "z' y2";
            else return "z' y";
        } else {
            if (front === 2) return "z2";
            else if (front === 3) return "x2";
            else if (front === 4) return "x2 y'";
            else return "x2 y";
        }
    }

    function changeMx3x3(v) {
        v = check3x3(v);
        let flag = 1;
        while (flag) {
            flag = 0;
            let flag1 = 0, state = [1, 2], s = "", firstChar = 0, n = 0;
            for (let j = 0; j < v.length; j++) {
                if (v.charAt(j) === "x" || v.charAt(j) === "y" || v.charAt(j) === "z") {
                    if (v.charAt(j + 1) === " " && v.length > j + 1) {
                        flag1 = 1;
                        n++;
                        if (n === 1) firstChar = j;
                        s = v.charAt(j);
                        state = currentState(state[0], state[1], s);
                        j++;
                        if (j !== v.length - 1) continue;
                    } else if (v.charAt(j + 2) === " " && v.length > j + 2) {
                        flag1 = 1;
                        n++;
                        if (n === 1) firstChar = j;
                        s = v.charAt(j) + v.charAt(j + 1);
                        state = currentState(state[0], state[1], s);
                        j += 2;
                        if (j !== v.length - 1) continue;
                    }
                }
                if (flag1) {
                    flag1 = 0;
                    let optimal = optimalByState(state[0], state[1]);
                    let n1 = n;
                    n = 0;
                    state = [1, 2];
                    if (n1 < 2) continue;
                    if (n1 === 2) {
                        if (optimal.length > 2) continue;
                        flag = 1;
                        v = v.substring(0, firstChar) + optimal + v.substring(j);
                        break;
                    } else {
                        flag = 1;
                        v = v.substring(0, firstChar) + optimal + v.substring(j);
                        break;
                    }
                }
            }
        }

        flag = 1;
        while (flag) {
            v = check3x3(v);
            flag = 0;
            let s = "", z = "", flag1 = 0;
            for (let j = 0; j < v.length; j++) {
                if (v.charAt(j) === "x" || v.charAt(j) === "y" || v.charAt(j) === "z") {
                    s = v.charAt(j);
                    if (v.charAt(j + 1) === " " && v.length > j + 1) {
                        z = "";
                        flag1 = 1;
                    } else if (v.charAt(j + 2) === " " && v.length > j + 2) {
                        z = v.charAt(j + 1);
                        flag1 = 1;
                    }
                }
                if (flag1) {
                    flag = 1;
                    if (s === "x" && z === "2") v = v.substring(0, j) + "Rw2 L2" + v.substring(j + 2);
                    if (s === "y" && z === "2") v = v.substring(0, j) + "Uw2 D2" + v.substring(j + 2);
                    if (s === "z" && z === "2") v = v.substring(0, j) + "Fw2 B2" + v.substring(j + 2);
                    if (s === "x" && z === "'") v = v.substring(0, j) + "Rw' L" + v.substring(j + 2);
                    if (s === "y" && z === "'") v = v.substring(0, j) + "Uw' D" + v.substring(j + 2);
                    if (s === "z" && z === "'") v = v.substring(0, j) + "Fw' B" + v.substring(j + 2);
                    if (s === "x" && z === "") v = v.substring(0, j) + "Rw L'" + v.substring(j + 1);
                    if (s === "y" && z === "") v = v.substring(0, j) + "Uw D'" + v.substring(j + 1);
                    if (s === "z" && z === "") v = v.substring(0, j) + "Fw B'" + v.substring(j + 1);
                    break;
                }
            }
        }


        v = check3x3(v);

        flag = 1;
        while (flag) {

            flag = 0;
            let s = "", z = "", flag1 = 0;
            for (let j = 0; j < v.length; j++) {
                if (v.charAt(j) === "M" || v.charAt(j) === "E" || v.charAt(j) === "S") {
                    s = v.charAt(j);
                    if (v.charAt(j + 1) === " " && v.length > j + 1) {
                        z = "";
                        flag1 = 1;
                    } else if (v.charAt(j + 2) === " " && v.length > j + 2) {
                        z = v.charAt(j + 1);
                        flag1 = 1;
                    }
                }
                if (flag1) {
                    flag = 1;
                    if (s === "M" && z === "2") v = v.substring(0, j) + "Rw2 R2" + v.substring(j + 2);
                    if (s === "S" && z === "2") v = v.substring(0, j) + "Fw2 F2" + v.substring(j + 2);
                    if (s === "E" && z === "2") v = v.substring(0, j) + "Uw2 U2" + v.substring(j + 2);
                    if (s === "M" && z === "'") v = v.substring(0, j) + "Rw R'" + v.substring(j + 2);
                    if (s === "S" && z === "'") v = v.substring(0, j) + "Fw' F" + v.substring(j + 2);
                    if (s === "E" && z === "'") v = v.substring(0, j) + "Uw U'" + v.substring(j + 2);
                    if (s === "M" && z === "") v = v.substring(0, j) + "Rw' R" + v.substring(j + 1);
                    if (s === "S" && z === "") v = v.substring(0, j) + "Fw F'" + v.substring(j + 1);
                    if (s === "E" && z === "") v = v.substring(0, j) + "Uw' U" + v.substring(j + 1);
                    break;
                }
            }
        }
        return v;
    }

    function optimize3x3(first, opt, v) {
        let g = ["L", "B", "D", "U", "F", "R", "X", "Y", "Z", "M", "S", "E", "W", " ", "2", "'"];

        v = changeOrder(first, v);

        v = check3x3(v);


        let flag = 1;
        while (flag === 1) {
            flag = 0;

            for (let j = 0; j < v.length; j++) {
                let s1 = "", s2 = "", w1 = "", w2 = "", z1 = "", z2 = "", flag1 = 0, ls = 0;
                for (let k = 0; k < 6; k++) {
                    if (v.charAt(j).toUpperCase() === g[k]) {
                        s1 = v.charAt(j);
                        if (s1 === "D") s2 = "U";
                        if (s1 === "L") s2 = "R";
                        if (s1 === "B") s2 = "F";
                        if (s1 === "U") s2 = "D";
                        if (s1 === "R") s2 = "L";
                        if (s1 === "F") s2 = "B";
                        flag1 = 1;
                        break
                    }
                }
                let flag2 = 0;
                if (flag1 === 1) {
                    if (v.charAt(j + 1) !== "w") {
                        w1 = "";
                        if (v.charAt(j + 1) === " " && v.length > j + 1) {
                            z1 = "";
                            if (v.charAt(j + 2) === s2 && v.charAt(j + 3) === "w") {
                                w2 = "w";
                                if (v.charAt(j + 4) === " " && v.length > j + 4) {
                                    flag2 = 1;
                                    z2 = "";
                                    ls = 4;
                                } else if (v.charAt(j + 5) === " " && v.length > j + 5) {
                                    flag2 = 1;
                                    z2 = v.charAt(j + 4);
                                    ls = 5;
                                }
                            }
                        } else {
                            z1 = v.charAt(j + 1);

                            if (v.charAt(j + 3) === s2 && v.charAt(j + 4) === "w") {
                                w2 = "w";
                                if (v.charAt(j + 5) === " " && v.length > j + 5) {
                                    flag2 = 1;
                                    z2 = "";
                                    ls = 5;
                                } else if (v.charAt(j + 6) === " " && v.length > j + 6) {
                                    flag2 = 1;
                                    z2 = v.charAt(j + 5);
                                    ls = 6;
                                }
                            }
                        }
                    } else {
                        w1 = "w";
                        if (v.charAt(j + 2) === " " && v.length > j + 2) {
                            z1 = "";
                            if (v.charAt(j + 3) === s2 && v.charAt(j + 4) !== w1) {
                                w2 = "";
                                if (v.charAt(j + 4) === " " && v.length > j + 4) {
                                    flag2 = 2;
                                    z2 = "";
                                    ls = 4;
                                } else if (v.charAt(j + 5) === " " && v.length > j + 5) {
                                    flag2 = 2;
                                    z2 = v.charAt(j + 4);
                                    ls = 5;
                                }
                            }
                        } else {
                            z1 = v.charAt(j + 2);

                            if (v.charAt(j + 4) === s2 && v.charAt(j + 5) !== w1) {
                                w2 = "";
                                if (v.charAt(j + 5) === " " && v.length > j + 5) {
                                    flag2 = 2;
                                    z2 = "";
                                    ls = 5;
                                } else if (v.charAt(j + 6) === " " && v.length > j + 6) {
                                    flag2 = 2;
                                    z2 = v.charAt(j + 5);
                                    ls = 6;
                                }
                            }

                        }
                    }
                    if (flag2 === 1 || flag2 === 2) {
                        if (z1 === "2" && z2 === "2" && opt) {
                            if (s1 === "R") v = v.substring(0, j) + "x2" + v.substring(j + ls + 1);
                            if (s1 === "L") v = v.substring(0, j) + "x2" + v.substring(j + ls + 1);
                            if (s1 === "U") v = v.substring(0, j) + "y2" + v.substring(j + ls + 1);
                            if (s1 === "D") v = v.substring(0, j) + "y2" + v.substring(j + ls + 1);
                            if (s1 === "F") v = v.substring(0, j) + "z2" + v.substring(j + ls + 1);
                            if (s1 === "B") v = v.substring(0, j) + "z2" + v.substring(j + ls + 1);
                        } else if (z1 === "" && z2 === "'" && opt) {
                            if (s1 === "R") v = v.substring(0, j) + "x" + v.substring(j + ls + 1);
                            if (s1 === "L") v = v.substring(0, j) + "x'" + v.substring(j + ls + 1);
                            if (s1 === "U") v = v.substring(0, j) + "y" + v.substring(j + ls + 1);
                            if (s1 === "D") v = v.substring(0, j) + "y'" + v.substring(j + ls + 1);
                            if (s1 === "F") v = v.substring(0, j) + "z" + v.substring(j + ls + 1);
                            if (s1 === "B") v = v.substring(0, j) + "z'" + v.substring(j + ls + 1);
                        } else if (z1 === "'" && z2 === "" && opt) {
                            if (s1 === "R") v = v.substring(0, j) + "x'" + v.substring(j + ls + 1);
                            if (s1 === "L") v = v.substring(0, j) + "x'" + v.substring(j + ls + 1);
                            if (s1 === "U") v = v.substring(0, j) + "y'" + v.substring(j + ls + 1);
                            if (s1 === "D") v = v.substring(0, j) + "y" + v.substring(j + ls + 1);
                            if (s1 === "F") v = v.substring(0, j) + "z'" + v.substring(j + ls + 1);
                            if (s1 === "B") v = v.substring(0, j) + "z" + v.substring(j + ls + 1);
                        } else if (flag2 === 1) {
                            flag = 1;
                            v = v.substring(0, j) + s2 + w2 + z2 + " " + s1 + w1 + z1 + v.substring(j + ls + 1);
                            break;
                        }
                    }

                }
            }

        }

        v = check3x3(v);


        flag = 1;
        while (flag === 1) {
            flag = 0;

            for (let j = 0; j < v.length; j++) {
                let s1 = "", w1 = "", w2 = "", z1 = "", z2 = "", flag1 = 0, ls = 0;
                for (let k = 0; k < 6; k++) {
                    if (v.charAt(j).toUpperCase() === g[k]) {
                        s1 = v.charAt(j);
                        flag1 = 1;
                        break
                    }
                }
                let flag2 = 0;
                if (flag1 === 1) {
                    if (v.charAt(j + 1) !== "w") {
                        w1 = "";
                        if (v.charAt(j + 1) === " " && v.length > j + 1) {
                            z1 = "";
                            if (v.charAt(j + 2) === s1 && v.charAt(j + 3) === "w") {
                                w2 = "w";
                                if (v.charAt(j + 4) === " " && v.length > j + 4) {
                                    flag2 = 1;
                                    z2 = "";
                                    ls = 4;
                                } else if (v.charAt(j + 5) === " " && v.length > j + 5) {
                                    flag2 = 1;
                                    z2 = v.charAt(j + 4);
                                    ls = 5;
                                }
                            }
                        } else {
                            z1 = v.charAt(j + 1);

                            if (v.charAt(j + 3) === s1 && v.charAt(j + 4) === "w") {
                                w2 = "w";
                                if (v.charAt(j + 5) === " " && v.length > j + 5) {
                                    flag2 = 1;
                                    z2 = "";
                                    ls = 5;
                                } else if (v.charAt(j + 6) === " " && v.length > j + 6) {
                                    flag2 = 1;
                                    z2 = v.charAt(j + 5);
                                    ls = 6;
                                }
                            }
                        }
                    } else {
                        w1 = "w";
                        if (v.charAt(j + 2) === " " && v.length > j + 2) {
                            z1 = "";
                            if (v.charAt(j + 3) === s1 && v.charAt(j + 4) !== w1) {
                                w2 = "";
                                if (v.charAt(j + 4) === " " && v.length > j + 4) {
                                    flag2 = 2;
                                    z2 = "";
                                    ls = 4;
                                } else if (v.charAt(j + 5) === " " && v.length > j + 5) {
                                    flag2 = 2;
                                    z2 = v.charAt(j + 4);
                                    ls = 5;
                                }
                            }
                        } else {
                            z1 = v.charAt(j + 2);

                            if (v.charAt(j + 4) === s1 && v.charAt(j + 5) !== w1) {
                                w2 = "";
                                if (v.charAt(j + 5) === " " && v.length > j + 5) {
                                    flag2 = 2;
                                    z2 = "";
                                    ls = 5;
                                } else if (v.charAt(j + 6) === " " && v.length > j + 6) {
                                    flag2 = 2;
                                    z2 = v.charAt(j + 5);
                                    ls = 6;
                                }
                            }

                        }
                    }
                    if (flag2 === 1 || flag2 === 2) {
                        if (z1 === "2" && z2 === "2" && opt) {
                            if (s1 === "R") v = v.substring(0, j) + "M2" + v.substring(j + ls + 1);
                            if (s1 === "L") v = v.substring(0, j) + "M2" + v.substring(j + ls + 1);
                            if (s1 === "U") v = v.substring(0, j) + "E2" + v.substring(j + ls + 1);
                            if (s1 === "D") v = v.substring(0, j) + "E2" + v.substring(j + ls + 1);
                            if (s1 === "F") v = v.substring(0, j) + "S2" + v.substring(j + ls + 1);
                            if (s1 === "B") v = v.substring(0, j) + "S2" + v.substring(j + ls + 1);
                        } else if ((w1 === "w" && z1 === "" && z2 === "'" || w1 === "" && z1 === "'" && z2 === "") && opt) {
                            if (s1 === "R") v = v.substring(0, j) + "M'" + v.substring(j + ls + 1);
                            if (s1 === "L") v = v.substring(0, j) + "M" + v.substring(j + ls + 1);
                            if (s1 === "U") v = v.substring(0, j) + "E'" + v.substring(j + ls + 1);
                            if (s1 === "D") v = v.substring(0, j) + "E" + v.substring(j + ls + 1);
                            if (s1 === "F") v = v.substring(0, j) + "S" + v.substring(j + ls + 1);
                            if (s1 === "B") v = v.substring(0, j) + "S'" + v.substring(j + ls + 1);
                        } else if ((w1 === "w" && z1 === "'" && z2 === "" || w1 === "" && z1 === "" && z2 === "'") && opt) {
                            if (s1 === "R") v = v.substring(0, j) + "M" + v.substring(j + ls + 1);
                            if (s1 === "L") v = v.substring(0, j) + "M'" + v.substring(j + ls + 1);
                            if (s1 === "U") v = v.substring(0, j) + "E" + v.substring(j + ls + 1);
                            if (s1 === "D") v = v.substring(0, j) + "E'" + v.substring(j + ls + 1);
                            if (s1 === "F") v = v.substring(0, j) + "S'" + v.substring(j + ls + 1);
                            if (s1 === "B") v = v.substring(0, j) + "S" + v.substring(j + ls + 1);
                        } else if (flag2 === 1) {
                            flag = 1;
                            v = v.substring(0, j) + s1 + w2 + z2 + " " + s1 + w1 + z1 + v.substring(j + ls + 1);
                            break;
                        }
                    }

                }
            }

        }

        v = check3x3(v);


        return v;
    }

    function check3x3(v) {
        let g = ["L", "B", "D", "U", "F", "R", "X", "Y", "Z", "M", "S", "E", "W", " ", "2", "'"];
        let flag = 1;
        v = v.toUpperCase();
        while (flag) {
            flag = 0;
            for (let j = 0; j < v.length; j++) {
                let flag1 = 0;
                for (let k = 0; k < g.length; k++) {
                    if (v.charAt(j) === g[k]) {
                        flag1 = 1;
                        break;
                    }
                }
                if (!flag1) {
                    v = v.substring(0, j) + v.substring(j + 1);
                    flag = 1;
                    break;
                }
            }
        }
        for (let j = 0; j < v.length; j++) {
            if (v.charAt(j) === "X") v = v.substring(0, j) + "x" + v.substring(j + 1);
            else if (v.charAt(j) === "Y") v = v.substring(0, j) + "y" + v.substring(j + 1);
            else if (v.charAt(j) === "Z") v = v.substring(0, j) + "z" + v.substring(j + 1);
            else if (v.charAt(j) === "W") v = v.substring(0, j) + "w" + v.substring(j + 1);
        }
        flag = 1;
        while (flag) {
            flag = 0;
            if (v.charAt(0) === " " || v.charAt(0) === "'"
                || v.charAt(0) === "2" || v.charAt(0) === "w") {
                flag = 1;
                v = v.substring(1);
            }
        }
        flag = 1;
        while (flag) {
            flag = 0;
            for (let j = 0; j < v.length; j++) {
                if (v.charAt(j) === " " && v.charAt(j - 1) === " ") {
                    v = v.substring(0, j) + v.substring(j + 1);
                    flag = 1;
                    break;
                }


                if (v.charAt(j) === "'" || v.charAt(j) === "2") {
                    if (v.charAt(j - 1) === "'" ||
                        v.charAt(j - 1) === "2") {
                        v = v.substring(0, j) + v.substring(j + 1);
                        flag = 1;
                        break;
                    } else if (v.charAt(j - 1) === " ") {
                        v = v.substring(0, j - 1) + v.substring(j);
                        flag = 1;
                        break;
                    }
                }

                if (v.charAt(j) === "w") {
                    if (!(v.charAt(j - 1) === "R" || v.charAt(j - 1) === "L" ||
                        v.charAt(j - 1) === "U" || v.charAt(j - 1) === "D" ||
                        v.charAt(j - 1) === "F" || v.charAt(j - 1) === "B" ||
                        v.charAt(j - 1) === " ")) {
                        v = v.substring(0, j) + v.substring(j + 1);
                        flag = 1;
                        break;
                    } else if (v.charAt(j - 1) === " ") {
                        v = v.substring(0, j - 1) + v.substring(j);
                        flag = 1;
                        break;
                    }
                }

                let flag1 = 0;
                for (let k = 0; k < 12; k++) {
                    if (v.charAt(j).toUpperCase() === g[k]) {
                        flag1 = 1;
                        break
                    }
                }
                if (flag1 && j !== 0) {
                    if (v.charAt(j - 1) !== " ") {
                        v = v.substring(0, j) + " " + v.substring(j);
                        flag = 1;
                        break;
                    }
                }
            }

        }

        flag = 1;
        while (flag === 1) {
            flag = 0;

            for (let j = 0; j < v.length; j++) {
                let s1 = "", w1 = "", z1 = "", z2 = "", flag1 = 0, ls = 0;
                for (let k = 0; k < 12; k++) {
                    if (v.charAt(j).toUpperCase() === g[k]) {
                        s1 = v.charAt(j);
                        flag1 = 1;
                        break
                    }
                }
                let flag2 = 0;
                if (flag1 === 1) {
                    if (v.charAt(j + 1) === "w") {
                        w1 = "w";
                        if (v.charAt(j + 2) === " " && v.length > j + 2) {
                            z1 = "";
                            if (v.charAt(j + 3) === s1 && v.charAt(j + 4) === w1) {
                                if (v.charAt(j + 5) === " " && v.length > j + 5) {
                                    flag2 = 1;
                                    z2 = "";
                                    ls = 5;
                                } else if (v.charAt(j + 6) === " " && v.length > j + 6) {
                                    flag2 = 1;
                                    z2 = v.charAt(j + 5);
                                    ls = 6;
                                }
                            }
                        } else {
                            z1 = v.charAt(j + 2);

                            if (v.charAt(j + 4) === s1 && v.charAt(j + 5) === w1) {
                                if (v.charAt(j + 6) === " " && v.length > j + 6) {
                                    flag2 = 1;
                                    z2 = "";
                                    ls = 6;
                                } else if (v.charAt(j + 7) === " " && v.length > j + 7) {
                                    flag2 = 1;
                                    z2 = v.charAt(j + 6);
                                    ls = 7;
                                }
                            }

                        }
                    } else {
                        w1 = "";
                        if (v.charAt(j + 1) === " " && v.length > j + 1) {
                            z1 = "";
                            if (v.charAt(j + 2) === s1) {
                                if (v.charAt(j + 3) === " " && v.length > j + 3) {
                                    flag2 = 1;
                                    z2 = "";
                                    ls = 3;
                                } else if (v.charAt(j + 3) !== "w" && v.charAt(j + 4) === " " && v.length > j + 4) {
                                    flag2 = 1;
                                    z2 = v.charAt(j + 3);
                                    ls = 4;
                                }
                            }
                        } else {
                            z1 = v.charAt(j + 1);

                            if (v.charAt(j + 3) === s1) {
                                if (v.charAt(j + 4) === " " && v.length > j + 4) {
                                    flag2 = 1;
                                    z2 = "";
                                    ls = 4;
                                } else if (v.charAt(j + 4) !== "w" && v.charAt(j + 5) === " " && v.length > j + 5) {
                                    flag2 = 1;
                                    z2 = v.charAt(j + 4);
                                    ls = 5;
                                }
                            }
                        }
                    }
                    if (flag2 === 1) {
                        console.log(j);
                        flag = 1;
                        if (z1 === "") z1 = 1;
                        else if (z1 === "2") z1 = 2;
                        else if (z1 === "'") z1 = 3;
                        if (z2 === "") z2 = 1;
                        else if (z2 === "2") z2 = 2;
                        else if (z2 === "'") z2 = 3;
                        z1 += z2;
                        z1 %= 4;
                        if (z1 === 0) v = v.substring(0, j) + v.substring(j + ls + 1);
                        else if (z1 === 1) v = v.substring(0, j) + " " + s1 + w1 + v.substring(j + ls + 1);
                        else if (z1 === 2) v = v.substring(0, j) + " " + s1 + w1 + "2" + v.substring(j + ls + 1);
                        else if (z1 === 3) v = v.substring(0, j) + " " + s1 + w1 + "'" + v.substring(j + ls + 1);
                        break;
                    }

                }
            }

        }
        return v;
    }

    function deleteLast(s) {
        let last = s.length - 1;
        for (let i = s.length - 1; i >= 0; i--) {
            if (s.charAt(i) === "R" || s.charAt(i) === "L" || s.charAt(i) === "U" || s.charAt(i) === "D"
                || s.charAt(i) === "F" || s.charAt(i) === "B" || s.charAt(i) === "w" || s.charAt(i) === "S"
                || s.charAt(i) === "E" || s.charAt(i) === "M") {
                break;
            } else last = i;
        }
        if (s.charAt(last + 1) === "'" || s.charAt(last + 1) === "2") last++;
        return s.substring(0, last + 1);
    }

    function deleteInner(s) {
        let l = 0, flag = 0, m = "", p = "";
        for (let i = s.length - 1; i >= 0; i--) {
            if ((s.charAt(i) === "R" || s.charAt(i) === "L" || s.charAt(i) === "U" || s.charAt(i) === "D"
                || s.charAt(i) === "F" || s.charAt(i) === "B") && !flag) {
                flag = 1;
                m = s.charAt(i);
            } else if ((s.charAt(i) === "R" || s.charAt(i) === "L" || s.charAt(i) === "U" || s.charAt(i) === "D"
                || s.charAt(i) === "F" || s.charAt(i) === "B") && flag) {
                flag = 0;
                break;
            } else if (s.charAt(i) === "x" || s.charAt(i) === "y" || s.charAt(i) === "z") {
                l = i;
                p = s.charAt(i);
                break;
            }
        }
        if (flag) {
            if (p === "x" && (m === "L" || m === "R") || p === "y" && (m === "U" || m === "D") || p === "z" && (m === "F" || m === "B")) {
                if (s.charAt(l + 1) === " ") return s.substring(0, l) + s.substring(l + 1);
                else return s.substring(0, l) + s.substring(l + 2);
            }
        }
        return s;
    }

    function input3x3() {
        let el = document.getElementsByClassName('input3x3');
        let g = ["B", "L", "D", "U", "F", "R", "X", "Y", "Z", "M", "S", "E", "W", " ", "2", "'"];
        for (let i = 0; i < el.length; i++) {
            el[i].addEventListener('input', event => {
                el[i].value = changeMx3x3(el[i].value);
                el[i].value = optimize3x3(1, 0, el[i].value);
                el[i].value = optimize3x3(0, 0, el[i].value);
                el[i].value = optimize3x3(1, 1, el[i].value);
                el[i].value = optimize3x3(0, 1, el[i].value);
            })
            el[i].addEventListener('change', event => {
                let flag = 1, v = el[i].value;
                while (flag) {
                    flag = 0;
                    if (el[i].value.charAt(el[i].value.length - 1) !== " ") el[i].value += " ";
                    el[i].value = changeMx3x3(el[i].value);
                    el[i].value = optimize3x3(1, 0, el[i].value);
                    if (el[i].value.charAt(el[i].value.length - 1) !== " ") el[i].value += " ";
                    el[i].value = optimize3x3(0, 0, el[i].value);
                    if (el[i].value.charAt(el[i].value.length - 1) !== " ") el[i].value += " ";
                    el[i].value = optimize3x3(1, 1, el[i].value);
                    if (el[i].value.charAt(el[i].value.length - 1) !== " ") el[i].value += " ";
                    el[i].value = optimize3x3(0, 1, el[i].value);
                    if (el[i].value !== v) {
                        v = el[i].value;
                        flag = 1;
                    }
                }
                el[i].value = deleteLast(el[i].value);
                el[i].value = deleteInner(el[i].value);
                el[i].value = check3x3(el[i].value);
                if (el[i].value.charAt(el[i].value.length - 1) === " ") el[i].value = el[i].value.substr(0, el[i].value.length - 1);
            })
        }
    }
})