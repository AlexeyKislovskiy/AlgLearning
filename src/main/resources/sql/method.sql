insert into method(id, name, cube_id, number_of_situations, description, learning, learned, image)
values (-1, 'Unknown', -1, -1, 'Unknown', -1, -1, '/static/img/icons/unknown.png');
insert into method(name, cube_id, number_of_situations, description, image)
values ('Pll', 1, 21,
        'До применения: собраны 2 слоя, ориентированы все элементы верхнего слоя. После применения: куб собран',
        '/static/img/methods/pllIntoSolved.png'),
       ('Oll', 1, 57, 'До применения: собраны 2 слоя. После применения: ориентированы все элементы' ||
                      ' верхнего слоя', '/static/img/methods/ollIntoSolved.png'),
       ('Cll', 2, 42, 'До применения: собран 1 слой. После применения: куб собран',
        '/static/img/methods/cllIntoSolved.png')